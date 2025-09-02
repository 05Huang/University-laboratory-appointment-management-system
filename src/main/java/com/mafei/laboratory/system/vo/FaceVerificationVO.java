package com.mafei.laboratory.system.vo;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class FaceVerificationVO {
    private Boolean verified;          // 人脸识别是否通过
    private Boolean hasReservation;    // 是否有有效预约
    private String message;            // 验证结果消息
    private Long userId;               // 用户ID
} 