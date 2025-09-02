package com.mafei.laboratory.system.service;

import com.mafei.laboratory.system.vo.FaceVerificationVO;
import org.springframework.web.multipart.MultipartFile;

public interface FaceRecognitionService {
    /**
     * 验证人脸并检查预约状态
     * @param file 人脸图片文件
     * @return 验证结果
     */
    FaceVerificationVO verifyFace(MultipartFile file);
} 