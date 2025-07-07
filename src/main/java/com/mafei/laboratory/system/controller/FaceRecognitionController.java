package com.mafei.laboratory.system.controller;

import com.mafei.laboratory.commons.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/face-recognition")
public class FaceRecognitionController {

    /**
     * 处理人脸识别请求
     */
    @PostMapping("/verify")
    public Result<Boolean> verifyFace(@RequestParam("file") MultipartFile file) {
        try {
            // 打印接收到的文件信息
            log.info("接收到人脸识别请求");
            log.info("文件名: {}", file.getOriginalFilename());
            log.info("文件大小: {} bytes", file.getSize());
            log.info("文件类型: {}", file.getContentType());

            // TODO: 这里后续添加人脸识别逻辑
            // 目前仅作为测试，直接返回成功
            return Result.ok(true);
        } catch (Exception e) {
            log.error("人脸识别处理失败: ", e);
            return Result.error("人脸识别失败：" + e.getMessage());
        }
    }
} 