package com.mafei.laboratory.system.service.impl;

import com.mafei.laboratory.commons.exception.BusinessException;
import com.mafei.laboratory.system.entity.SysUserFace;
import com.mafei.laboratory.system.repository.SysUserFaceRepository;
import com.mafei.laboratory.system.service.SysUserFaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
public class SysUserFaceServiceImpl implements SysUserFaceService {

    @Value("${face.image.upload.path}")
    private String faceImageUploadPath;

    private final SysUserFaceRepository sysUserFaceRepository;

    public SysUserFaceServiceImpl(SysUserFaceRepository sysUserFaceRepository) {
        this.sysUserFaceRepository = sysUserFaceRepository;
    }

    @PostConstruct
    public void init() {
        try {
            Path uploadDir = Paths.get(faceImageUploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
                log.info("创建人脸图片上传根目录：{}", uploadDir);
            }
        } catch (IOException e) {
            log.error("创建人脸图片上传根目录失败：{}", e.getMessage());
            throw new BusinessException("初始化人脸图片上传目录失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserFace saveFaceImage(Long userId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("上传的文件不能为空");
        }

        String originalFilename = file.getOriginalFilename();
        if (!StringUtils.hasText(originalFilename)) {
            throw new BusinessException("文件名不能为空");
        }

        // 检查文件类型
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!".jpg".equals(extension) && !".jpeg".equals(extension) && !".png".equals(extension)) {
            throw new BusinessException("只支持JPG、JPEG和PNG格式的图片");
        }

        try {
            log.info("开始保存用户[{}]的人脸图片，原始文件名：{}", userId, originalFilename);

            // 创建上传目录
            String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String uploadPath = faceImageUploadPath + File.separator + datePath;
            Path directory = Paths.get(uploadPath);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
                log.info("创建上传目录：{}", directory);
            }

            // 生成文件名
            String fileName = UUID.randomUUID().toString() + extension;
            Path filePath = directory.resolve(fileName);
            log.info("生成的文件路径：{}", filePath);

            // 保存文件
            Files.copy(file.getInputStream(), filePath);
            log.info("文件保存成功");

            // 保存数据库记录
            String relativePath = datePath + File.separator + fileName;
            SysUserFace userFace = sysUserFaceRepository.findByUserId(userId)
                    .orElse(new SysUserFace());

            userFace.setUserId(userId);
            userFace.setFaceImagePath(relativePath);
            userFace.setStatus(1);

            userFace = sysUserFaceRepository.save(userFace);
            log.info("数据库记录保存成功，ID：{}", userFace.getId());

            return userFace;
        } catch (IOException e) {
            log.error("保存人脸图片失败：", e);
            throw new BusinessException("保存人脸图片失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("保存人脸信息时发生未知错误：", e);
            throw new BusinessException("保存人脸信息失败：" + e.getMessage());
        }
    }

    @Override
    public SysUserFace getFaceByUserId(Long userId) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }
        return sysUserFaceRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException("未找到用户人脸信息"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFaceByUserId(Long userId) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }

        SysUserFace userFace = getFaceByUserId(userId);
        
        // 删除物理文件
        Path filePath = Paths.get(faceImageUploadPath, userFace.getFaceImagePath());
        try {
            boolean deleted = Files.deleteIfExists(filePath);
            log.info("删除文件{}：{}", filePath, deleted ? "成功" : "文件不存在");
        } catch (IOException e) {
            log.error("删除人脸图片文件失败：", e);
            throw new BusinessException("删除人脸图片文件失败：" + e.getMessage());
        }
        
        // 删除数据库记录
        sysUserFaceRepository.deleteByUserId(userId);
        log.info("删除用户[{}]的人脸信息成功", userId);
    }
} 