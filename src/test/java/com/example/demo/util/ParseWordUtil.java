package com.example.demo.util;


import com.example.demo.model.entity.RawFile;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import java.io.*;

/**
 * @author: rich
 * @date: 2023/3/28 14:49
 * @description:
 */

public class ParseWordUtil {

    public static void main(String[] args) {
        ParseWordUtil.parse("C:\\Users\\rich\\Desktop\\2023-标书制作\\技术规范书-学员数字人工具开发.docx");
    }

    public static String parse(String path) {
        Tika tika = new Tika();
        try {
            String text = tika.parseToString(new File(path));
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
                        sb.append(":" + textBlocks[i++]);
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
                        System.out.println();textBlocks[i].split("u4e00-u9fa5");
                        sb.append(textBlocks[i++].replaceAll("\\d+", "").replaceAll("\\.", "").replaceAll("（", "").replaceAll("）", ""));
                        rawFile.getOther().add(sb.toString());
                    }
                }
                if (textBlocks[i].contains("9.") && textBlocks[i].contains("附件")) rawFile.setAttachment(textBlocks[++i]);
            }
            System.out.println("  ");
        } catch (IOException | TikaException e) {
            e.printStackTrace();
        }
        return null;
    }


}
