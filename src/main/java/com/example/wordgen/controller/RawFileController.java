package com.example.wordgen.controller;

import com.example.wordgen.model.entity.RawFile;
import com.example.wordgen.model.response.ApiResponse;
import com.example.wordgen.service.RawFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: rich
 * @date: 2023/4/3 15:06
 * @description:
 */
@RestController
@Slf4j
@RequestMapping("rawfile")
public class RawFileController {

    @Autowired
    private RawFileService rawFileService;

    @PostMapping("uploadWordFile")
    public ApiResponse uploadWordFile(@RequestParam("file") MultipartFile file) {
        if (log.isInfoEnabled()) {
            log.info("/rawfile/uploadWordFile");
        }
        return ApiResponse.success(rawFileService.uploadWordFile(file));
    }

    @GetMapping("getRawText")
    public ApiResponse getRawText(@RequestParam("rawTextId") int RawTextId) {
        if (log.isInfoEnabled()) {
            log.info("/rawfile/getRawText");
        }
        return ApiResponse.success(rawFileService.getRawFile(RawTextId));
    }

    @PutMapping("updateRawText")
    public ApiResponse updateRawText(@RequestBody RawFile rawFile) {
        if (log.isInfoEnabled()) {
            log.info("/rawfile/updateRawText");
        }
        rawFileService.updateRawFile(rawFile);
        return ApiResponse.success(null);
    }
}
