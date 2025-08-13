package com.mafei.laboratory.system.service;

import org.springframework.web.multipart.MultipartFile;

public interface FaceRecognitionService {
    /**
     * 验证人脸
     * @param file 上传的人脸图片
     * @return 是否验证通过
     */
    boolean verifyFace(MultipartFile file);
} 