package com.example.wordgen.service;

import com.example.wordgen.exception.ServiceException;
import com.example.wordgen.repository.RawFileRepository;
import com.example.wordgen.model.entity.RawFile;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Optional;

/**
 * @author: rich
 * @date: 2023/4/3 15:09
 * @description:
 */
@Service
public class RawFileService {

    @Autowired
    RawFileRepository rawFileRepository;

    public RawFile uploadWordFile(MultipartFile file) {
        Tika tika = new Tika();
        try {
            if (!file.getOriginalFilename().endsWith(".docx")) throw new ServiceException("文件不符合规范");
            String text = tika.parseToString(file.getInputStream());
            String[] textBlocks = text.split("\n");
            RawFile rawFile = new RawFile();
            for (int i = 0; i < textBlocks.length; i++) {
                if (textBlocks[i].contains("1.1") && textBlocks[i].contains("项目名称")) rawFile.setName(textBlocks[++i]);
                if (textBlocks[i].contains("1.2") && textBlocks[i].contains("实施地点")) rawFile.setPost(textBlocks[++i]);
                if (textBlocks[i].contains("1.3") && textBlocks[i].contains("采购项目概况")) rawFile.setSurvey(textBlocks[++i]);
                if (textBlocks[i].matches("\t\\d+"))
                    rawFile.getPurchaseRanges().add("服务名称为" + textBlocks[++i].replaceAll("\t", "") + "。服务内容为" + textBlocks[++i].replaceAll("\t", ""));
                if (textBlocks[i].contains("3.") && textBlocks[i].contains("服务周期")) rawFile.setPeriod(textBlocks[++i]);
                if (textBlocks[i].contains("4.") && textBlocks[i].contains("服务具体内容")) {
                    i++;
                    while (!(textBlocks[i].contains("5.") && textBlocks[i].contains("人员要求"))) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(textBlocks[i++].replaceAll("\\d+", "").replaceAll("\\.", "").replaceAll("（", "").replaceAll("）", ""));
                        sb.append(":").append(textBlocks[i++]);
                        rawFile.getService().add(sb.toString());
                    }
                }
                if (textBlocks[i].contains("5.") && textBlocks[i].contains("人员要求")) rawFile.setManpower(textBlocks[++i]);
                if (textBlocks[i].contains("6.1") && textBlocks[i].contains("成果要求")) rawFile.setAchievement(textBlocks[++i]);
                if (textBlocks[i].contains("6.2") && textBlocks[i].contains("验收")) rawFile.setAcceptance(textBlocks[++i]);
                if (textBlocks[i].contains("6.3") && textBlocks[i].contains("付款方式")) rawFile.setPayment(textBlocks[++i]);
                if (textBlocks[i].contains("7.") && textBlocks[i].contains("知识产权归属")) rawFile.setOwnership(textBlocks[++i]);
                if (textBlocks[i].contains("8.") && textBlocks[i].contains("其他要求")) {
                    i++;
                    while (!textBlocks[i].contains("附件")) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(textBlocks[i++].replaceAll("\\d+", "").replaceAll("\\.", "").replaceAll("（", "").replaceAll("）", ""));
                        rawFile.getOther().add(sb.toString());
                    }
                }
                if (textBlocks[i].contains("9.") && textBlocks[i].contains("附件")) rawFile.setAttachment(textBlocks[++i]);
            }
            return rawFileRepository.save(rawFile);
        } catch (IOException | TikaException e) {
            e.printStackTrace();
            throw new ServiceException("Tika模块处理错误");
        }
    }

    public RawFile getRawFile(Integer rawFileId) {
        Optional<RawFile> rawFileOptional = rawFileRepository.findById(rawFileId);
        if (!rawFileOptional.isPresent()) throw new ServiceException("该原文件记录不存在，请重新操作.");
        return rawFileOptional.get();
    }

    public void updateRawFile(RawFile rawFile) {
        Optional<RawFile> rawFileOptional = rawFileRepository.findById(rawFile.getId());
        if (!rawFileOptional.isPresent()) throw new ServiceException("该原文件记录不存在，请重新操作.");
        rawFileRepository.save(rawFile);
    }



}
