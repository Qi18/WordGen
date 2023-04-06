package com.example.wordgen.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.wordgen.exception.ServiceException;
import com.example.wordgen.model.entity.File;
import com.example.wordgen.model.entity.FileRawFile;
import com.example.wordgen.model.entity.HistoryInfo;
import com.example.wordgen.model.entity.RawFile;
import com.example.wordgen.model.request.ReGenParam;
import com.example.wordgen.repository.FileRawFileRepository;
import com.example.wordgen.repository.FileRepository;
import com.example.wordgen.repository.HistoryInfoRepository;
import com.example.wordgen.repository.RawFileRepository;
import com.example.wordgen.util.DocUtils;
import com.example.wordgen.util.OpenAIChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author: rich
 * @date: 2023/4/4 14:16
 * @description:
 */
@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    RawFileRepository rawFileRepository;

    @Autowired
    FileRawFileRepository fileRawFileRepository;

    @Autowired
    HistoryInfoRepository historyInfoRepository;

    @Transactional
    public File genTextThroughChatGpt(Integer RawTextId) {
        String project = summaryProject(RawTextId);
        File file = new File();
        file.setUnderstanding(chat(project + "根据上述项目，从项目内容、项目目标、交付成果、交付时间、部署方式的角度，分别阐述一下对这个项目的理解", RawTextId));
        file.setSchedule(chat("根据上述项目，结合交付成果与交付时间，列一个工作计划", RawTextId));
        file.setQuality(chat("根据上述项目，从过往业绩、技术团队、公司投入、代码管理、质量管理、数据安全治理等角度，阐述一下履约能力及质量保证措施", RawTextId));
        file.setCommitment(chat("根据上述项目，写一个和保修与售后服务相关的服务承诺", RawTextId));
        file.setBack(chat("根据上述项目，从技术方案的角度，探索一下采购文件“应答人须知前附表附件（1）技术评分标准”涉及的支撑材料", RawTextId));
        file = fileRepository.save(file);
        fileRawFileRepository.save(new FileRawFile().builder().fileId(file.getId()).rawFileId(RawTextId).build());
        return file;
    }

    public String summaryProject(Integer RawTextId) {
        Optional<RawFile> rawFileOptional = rawFileRepository.findById(RawTextId);
        if (!rawFileOptional.isPresent()) throw new ServiceException("对应的原文件不存在，请重新操作");
        RawFile rawFile = rawFileOptional.get();
        StringBuilder sb = new StringBuilder();
        sb.append("项目如下:\n");
        sb.append("项目名称是" + rawFile.getName() + "\n");
        sb.append("采购项目概况:" + rawFile.getSurvey() + "\n");
        sb.append("项目实施地点是" + rawFile.getPost() + "\n");
        sb.append("项目采购范围包括" + rawFile.getPurchaseRanges()+ "\n");
        sb.append("项目服务周期:" + rawFile.getPeriod()+ "\n");
        sb.append("项目服务具体内容:" + rawFile.getService()+ "\n");
        sb.append("项目的人员要求:" + rawFile.getManpower()+ "\n");
        sb.append("项目的成果:" + rawFile.getAchievement()+ "\n");
        sb.append("项目的验收:" + rawFile.getAcceptance()+ "\n");
        sb.append("项目的付款方式:" + rawFile.getPayment()+ "\n");
        sb.append("项目的知识产权归属:" + rawFile.getOwnership()+ "\n");
        sb.append("项目的其他要求:" + rawFile.getOther()+ "\n");
        sb.append("项目的附件:" + rawFile.getAttachment()+ "\n");
        return sb.toString();
    }

    public String chat(String question, Integer RawTextId) {
        Optional<HistoryInfo> historyInfoOptional = historyInfoRepository.findByRawFileId(RawTextId);
        String historyInfo = "";
        if (historyInfoOptional.isPresent()) historyInfo = historyInfoOptional.get().getHistory();
        if (historyInfo == null || historyInfo.equals("")) {
            //初始化请求参数体
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("model", "gpt-3.5-turbo");
            paramMap.put("messages", new ArrayList<>());
            historyInfo = JSONUtil.toJsonStr(paramMap);
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("messages", new HashMap<String, String>() {{
            put("role", "user");
            put("content", question);
        }});
        String newRecord = JSONUtil.toJsonStr(paramMap);
        historyInfo = addRecord(newRecord, historyInfo);
        JSONObject param = JSONUtil.parseObj(historyInfo);
        String body = HttpRequest.post(OpenAIChat.chatEndpoint)
                .header("Authorization", OpenAIChat.apiKey)
                .header("Content-Type", "application/json")
                .body(JSONUtil.parse(param))
                .execute()
                .body();
        JSONObject jsonObject = JSONUtil.parseObj(body);
        JSONArray choices = jsonObject.getJSONArray("choices");
        JSONObject result = choices.get(0, JSONObject.class, Boolean.TRUE);
        JSONObject message = result.getJSONObject("message");
        //记录请求参数体
        JSONArray temp = param.getJSONArray("messages");
        temp.add(message);
        param.put("messages", temp);
        historyInfo = param.toString();
        if (historyInfoOptional.isPresent()) {
            HistoryInfo temp1 = historyInfoOptional.get();
            temp1.setHistory(historyInfo);
            historyInfoRepository.save(temp1);
        }
        else historyInfoRepository.save(new HistoryInfo().builder().rawFileId(RawTextId).history(historyInfo).build());
        return (String) message.get("content");
    }

    public String addRecord(String record, String historyInfo) {
        JSONObject param = JSONUtil.parseObj(historyInfo);
        JSONObject recordJSON = JSONUtil.parseObj(record);
        JSONArray temp = param.getJSONArray("messages");
        temp.add(recordJSON.get("messages"));
        param.put("messages", temp);
        return param.toString();
    }

    public String genTextWithContent(ReGenParam reGenParam) {
        Optional<FileRawFile> fileRawFileOptional = fileRawFileRepository.findByRawFileId(reGenParam.getId());
        if (!fileRawFileOptional.isPresent()) throw new ServiceException("原文档不存在和生成的连接记录，请重新操作");
        Optional<File> fileOptional = fileRepository.findById(fileRawFileOptional.get().getFileId());
        if (!fileOptional.isPresent()) throw new ServiceException("生成文档不存在，请重新操作");
        File file = fileOptional.get();
        if (reGenParam.getIndex().equals("understanding")){
            String answer = chat("根据上述项目，从项目内容、项目目标、交付成果、交付时间、部署方式的角度，分别阐述一下对这个项目的理解。同时" + reGenParam.getContent() , reGenParam.getId());
            file.setUnderstanding(answer);
            fileRepository.save(file);
            return answer;
        }
        if (reGenParam.getIndex().equals("schedule")) {
            String answer = chat("根据上述项目，结合交付成果与交付时间，列一个工作计划。同时" + reGenParam.getContent() , reGenParam.getId());
            file.setSchedule(answer);
            fileRepository.save(file);
            return answer;
        }
        if (reGenParam.getIndex().equals("quality")) {
            String answer = chat("根据上述项目，从过往业绩、技术团队、公司投入、代码管理、质量管理、数据安全治理等角度，阐述一下履约能力及质量保证措施。同时" + reGenParam.getContent() , reGenParam.getId());
            file.setQuality(answer);
            fileRepository.save(file);
            return answer;
        }
        if (reGenParam.getIndex().equals("commitment")) {
            String answer = chat("根据上述项目，写一个和保修与售后服务相关的服务承诺。同时" + reGenParam.getContent() , reGenParam.getId());
            file.setCommitment(answer);
            fileRepository.save(file);
            return answer;
        }
        if (reGenParam.getIndex().equals("back")) {
            String answer = chat("根据上述项目，从技术方案的角度，探索一下采购文件“应答人须知前附表附件（1）技术评分标准”涉及的支撑材料。同时" + reGenParam.getContent() , reGenParam.getId());
            file.setBack(answer);
            fileRepository.save(file);
            return answer;
        }
        throw new ServiceException("请求参数有误，请重新操作");
    }

    public void updateText(File file) {
        Optional<File> fileOptional = fileRepository.findById(file.getId());
        if (!fileOptional.isPresent()) throw new ServiceException("不存在此生成文档");
        fileRepository.save(file);
    }

    public File getFile(Integer fileId) {
        Optional<File> fileOptional = fileRepository.findById(fileId);
        if (!fileOptional.isPresent()) throw new ServiceException("该生成文件记录不存在，请重新操作.");
        return fileOptional.get();
    }

    public Map<String, Object> genWordFile(Integer fileId) {
        Optional<File> fileOptional = fileRepository.findById(fileId);
        if (!fileOptional.isPresent()) throw new ServiceException("生成文件不存在，请重新操作");
        File file = fileOptional.get();
        Map<String, Object> dataMap = new HashMap<String, Object>(){{
            put("understanding", file.getUnderstanding());
            put("schedule", file.getSchedule());
            put("commitment", file.getCommitment());
            put("quality", file.getQuality());
            put("back", file.getBack());
        }};
//        String templateName = "template.ftl";
//        DocUtils.generateWord(dataMap, templateName, "C:\\Users\\rich\\Desktop\\demo.docx");
        return dataMap;
    }
}
