package com.mafei.laboratory.system.service;

import com.mafei.laboratory.system.entity.SysUserFace;
import org.springframework.web.multipart.MultipartFile;

public interface SysUserFaceService {
    
    /**
     * 保存用户人脸信息
     *
     * @param userId 用户ID
     * @param file   人脸图片文件
     * @return 保存后的用户人脸信息
     */
    SysUserFace saveFaceImage(Long userId, MultipartFile file);
    
    /**
     * 获取用户人脸信息
     *
     * @param userId 用户ID
     * @return 用户人脸信息
     */
    SysUserFace getFaceByUserId(Long userId);
    
    /**
     * 删除用户人脸信息
     *
     * @param userId 用户ID
     */
    void deleteFaceByUserId(Long userId);
} 