package com.mafei.laboratory.system.controller;

import com.mafei.laboratory.commons.utils.Result;
import com.mafei.laboratory.system.entity.SysUserFace;
import com.mafei.laboratory.system.service.SysUserFaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户人脸信息管理
 */
@Slf4j
@RestController
@RequestMapping("/api/userFace")
public class SysUserFaceController {

    private final SysUserFaceService sysUserFaceService;

    public SysUserFaceController(SysUserFaceService sysUserFaceService) {
        this.sysUserFaceService = sysUserFaceService;
    }

    /**
     * 上传用户人脸图片
     */
    @PostMapping("/upload")
    public Result<SysUserFace> uploadFace(@RequestParam("userId") Long userId, @RequestParam("file") MultipartFile file) {
        try {
            log.info("开始处理用户[{}]的人脸图片上传请求", userId);
            if (userId == null) {
                return Result.error("用户ID不能为空");
            }
            if (file == null || file.isEmpty()) {
                return Result.error("上传的文件不能为空");
            }
            
            SysUserFace userFace = sysUserFaceService.saveFaceImage(userId, file);
            log.info("用户[{}]的人脸图片上传成功", userId);
            return Result.ok(userFace);
        } catch (Exception e) {
            log.error("用户[{}]的人脸图片上传失败：", userId, e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取用户人脸信息
     */
    @GetMapping("/{userId}")
    public Result<SysUserFace> getFace(@PathVariable Long userId) {
        try {
            log.info("开始获取用户[{}]的人脸信息", userId);
            if (userId == null) {
                return Result.error("用户ID不能为空");
            }
            
            SysUserFace userFace = sysUserFaceService.getFaceByUserId(userId);
            log.info("获取用户[{}]的人脸信息成功", userId);
            return Result.ok(userFace);
        } catch (Exception e) {
            log.error("获取用户[{}]的人脸信息失败：", userId, e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除用户人脸信息
     */
    @DeleteMapping("/{userId}")
    public Result<Void> deleteFace(@PathVariable Long userId) {
        try {
            log.info("开始删除用户[{}]的人脸信息", userId);
            if (userId == null) {
                return Result.error("用户ID不能为空");
            }
            
            sysUserFaceService.deleteFaceByUserId(userId);
            log.info("删除用户[{}]的人脸信息成功", userId);
            return Result.ok();
        } catch (Exception e) {
            log.error("删除用户[{}]的人脸信息失败：", userId, e);
            return Result.error(e.getMessage());
        }
    }
} 