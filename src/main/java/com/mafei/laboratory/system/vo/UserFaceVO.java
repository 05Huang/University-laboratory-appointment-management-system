package com.mafei.laboratory.system.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户人脸信息VO
 */
@Data
public class UserFaceVO {
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 人脸图片文件
     */
    private MultipartFile faceImage;
} 