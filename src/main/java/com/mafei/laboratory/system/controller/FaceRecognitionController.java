package com.mafei.laboratory.system.controller;

import com.mafei.laboratory.commons.utils.Result;
import com.mafei.laboratory.system.service.FaceRecognitionService;
import com.mafei.laboratory.system.vo.FaceVerificationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/api/face-recognition")
public class FaceRecognitionController {

    @Resource
    private FaceRecognitionService faceRecognitionService;

    /**
     * 处理人脸识别请求
     */
    @PostMapping("/verify")
    public Result<FaceVerificationVO> verifyFace(@RequestParam("file") MultipartFile file) {
        try {
            // 打印接收到的文件信息
            log.info("接收到人脸识别请求");
            log.info("文件名: {}", file.getOriginalFilename());
            log.info("文件大小: {} bytes", file.getSize());
            log.info("文件类型: {}", file.getContentType());

            FaceVerificationVO result = faceRecognitionService.verifyFace(file);
            
            if (!result.getVerified()) {
                return Result.error(result.getMessage());
            }
            
            if (!result.getHasReservation()) {
                return Result.error(result.getMessage());
            }
            
            return Result.ok(result);
        } catch (Exception e) {
            log.error("人脸识别处理失败: ", e);
            return Result.error("人脸识别失败：" + e.getMessage());
        }
    }
} 