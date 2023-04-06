package com.example.wordgen.util;

import com.example.wordgen.exception.ServiceException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import lombok.experimental.UtilityClass;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author: rich
 * @date: 2023/4/4 19:29
 * @description:
 */
@UtilityClass
public class DocUtils {

    public void generateWord(Map<String, Object> dataMap, String templateName, Writer writer) {

        // 设置FreeMarker的版本和编码格式
        Configuration configuration = new Configuration(new Version("2.3.28"));
        configuration.setDefaultEncoding("UTF-8");

        // 设置FreeMarker生成Word文档所需要的模板的路径
        // configuration.setDirectoryForTemplateLoading(new File("/Users/xxx/Desktop/"));
        // 此处把模版文件都放在 resources 下的 templates 中
        configuration.setClassForTemplateLoading(DocUtils.class, "/templates");

        // 设置FreeMarker生成Word文档所需要的模板
        try {
            Template tem = configuration.getTemplate(templateName, "UTF-8");
            // FreeMarker使用Word模板和数据生成Word文档
            tem.process(dataMap, writer);
            writer.flush();
            writer.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            throw new ServiceException("word模板使用错误");
        }
    }

}
