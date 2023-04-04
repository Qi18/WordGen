package com.example.wordgen.controller;

import com.example.wordgen.model.entity.File;
import com.example.wordgen.model.request.ReGenParam;
import com.example.wordgen.model.response.ApiResponse;
import com.example.wordgen.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse storeWordFile(@RequestParam("fileId") Integer fileId) {
        if (log.isInfoEnabled()) {
            log.info("/proc/storeWordFile");
        }
        return ApiResponse.success(fileService.genWordFile(fileId));
    }
}
