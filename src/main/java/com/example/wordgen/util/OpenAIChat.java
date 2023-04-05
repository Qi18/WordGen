package com.example.demo.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.demo.model.entity.HistoryInfo;
import com.example.demo.repository.HistoryInfoRepository;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author: rich
 * @date: 2023/4/4 14:35
 * @description:
 */
@UtilityClass
public class OpenAIChat {

    public String chatEndpoint = "https://chat.f03.me/v1/chat/completions";

    public String apiKey = "Bearer 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";

    @Resource
    HistoryInfoRepository historyInfoRepository;

//    public String chat(String question, Integer RawTextId) {
//        Optional<HistoryInfo> historyInfoOptional = historyInfoRepository.findByRawFileId(RawTextId);
//        String historyInfo = "";
//        if (historyInfoOptional.isPresent()) historyInfo = historyInfoOptional.get().getHistory();
//        JSONObject param = null;
//        if (historyInfo == null || historyInfo.equals("")) {
//            //初始化请求参数体
//            Map<String, Object> paramMap = new HashMap<>();
//            paramMap.put("model", "gpt-3.5-turbo");
//            paramMap.put("messages", new ArrayList<>());
//            historyInfo = JSONUtil.toJsonStr(paramMap);
//        }
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("messages", new HashMap<String, String>() {{
//            put("role", "user");
//            put("content", question);
//        }});
//        String newRecord = JSONUtil.toJsonStr(paramMap);
//        historyInfo = addRecord(newRecord, historyInfo);
//        param = JSONUtil.parseObj(historyInfo);
//        String body = HttpRequest.post(chatEndpoint)
//                .header("Authorization", apiKey)
//                .header("Content-Type", "application/json")
//                .body(JSONUtil.parse(param))
//                .execute()
//                .body();
//        JSONObject jsonObject = JSONUtil.parseObj(body);
//        JSONArray choices = jsonObject.getJSONArray("choices");
//        JSONObject result = choices.get(0, JSONObject.class, Boolean.TRUE);
//        JSONObject message = result.getJSONObject("message");
//        //记录请求参数体
//        JSONArray temp = param.getJSONArray("messages");
//        temp.add(message);
//        param.put("messages", temp);
//        historyInfo = param.toString();
//        if (historyInfoOptional.isPresent()) {
//            HistoryInfo temp1 = historyInfoOptional.get();
//            temp1.setHistory(historyInfo);
//            historyInfoRepository.save(temp1);
//        }
//        else historyInfoRepository.save(new HistoryInfo().builder().rawFileId(RawTextId).history(historyInfo).build());
//        return (String) message.get("content");
//    }
//
//    public String addRecord(String record, String historyInfo) {
//        JSONObject param = JSONUtil.parseObj(historyInfo);
//        JSONObject recordJSON = JSONUtil.parseObj(record);
//        JSONArray temp = param.getJSONArray("messages");
//        temp.add(recordJSON.get("messages"));
//        param.put("messages", temp);
//        return param.toString();
//    }
}
