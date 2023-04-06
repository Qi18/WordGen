package com.example.wordgen.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.word.WordUtil;
import com.example.wordgen.model.entity.File;
import com.example.wordgen.model.request.ReGenParam;
import com.example.wordgen.model.response.ApiResponse;
import com.example.wordgen.service.FileService;
import com.example.wordgen.util.DocUtils;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

/**
 * @author: rich
 * @date: 2023/4/3 15:07
 * @description:
 */
@RestController
@RequestMapping("proc")
@Slf4j
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("genText")
    public ApiResponse genText(@RequestParam("rawTextId") Integer RawTextId) {
        if (log.isInfoEnabled()) {
            log.info("/proc/genText");
        }
        return ApiResponse.success(fileService.genTextThroughChatGpt(RawTextId));
    }

    @PostMapping("genTextWithContent")
    public ApiResponse genTextWithContent(@RequestBody ReGenParam reGenParam) {
        if (log.isInfoEnabled()) {
            log.info("/proc/genTextWithContent");
        }
        return ApiResponse.success(fileService.genTextWithContent(reGenParam));
    }

    @PostMapping("updateText")
    public ApiResponse updateText(@RequestBody File file) {
        if (log.isInfoEnabled()) {
            log.info("/proc/updateText");
        }
        fileService.updateText(file);
        return ApiResponse.success(null);
    }

    @GetMapping("getText")
    public ApiResponse getRawText(@RequestParam("fileId") int fileId) {
        if (log.isInfoEnabled()) {
            log.info("/proc/getText");
        }
        return ApiResponse.success(fileService.getFile(fileId));
    }

    @GetMapping("storeWordFile")
    public ApiResponse storeWordFile(HttpServletResponse response, @RequestParam("fileId") Integer fileId) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("/proc/storeWordFile");
        }
        //生成word文件设置
        String fileName = URLEncoder.encode("生成文档" + DateUtil.format(new Date(), "yyyyMMddHHmmss"), "UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName + ".docx");
        Writer writer = response.getWriter();
        Map<String, Object> dataMap = fileService.genWordFile(fileId);
        String templateName = "template.ftl";
        DocUtils.generateWord(dataMap, templateName, writer);
        return ApiResponse.success(null);
    }
}
