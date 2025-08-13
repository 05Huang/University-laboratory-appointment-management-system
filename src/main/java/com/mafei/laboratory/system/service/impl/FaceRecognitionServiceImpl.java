package com.mafei.laboratory.system.service.impl;

import com.mafei.laboratory.commons.utils.FaceMatchUtil;
import com.mafei.laboratory.system.entity.SysUserFace;
import com.mafei.laboratory.system.repository.SysUserFaceRepository;
import com.mafei.laboratory.system.service.FaceRecognitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FaceRecognitionServiceImpl implements FaceRecognitionService {

    @Resource
    private SysUserFaceRepository sysUserFaceRepository;

    @Value("${baidu.face.access-token}")
    private String accessToken;

    private static final String TEMP_FACE_PATH = "D:\\laboratory\\menjin\\";
    private static final String FACE_IMAGE_BASE_PATH = "D:\\laboratory\\face-images\\";
    private static final double SIMILARITY_THRESHOLD = 80.0;

    @Override
    public boolean verifyFace(MultipartFile file) {
        try {
            // 确保临时目录存在
            File tempDir = new File(TEMP_FACE_PATH);
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }

            // 生成临时文件名并保存上传的图片
            String tempFileName = UUID.randomUUID().toString() + ".jpg";
            Path tempFilePath = Paths.get(TEMP_FACE_PATH, tempFileName);
            Files.write(tempFilePath, file.getBytes());

            // 获取所有启用状态的人脸记录
            List<SysUserFace> activeFaces = sysUserFaceRepository.findAllActiveFaces();
            
            try {
                // 遍历比对所有启用的人脸
                for (SysUserFace face : activeFaces) {
                    String dbImagePath = FACE_IMAGE_BASE_PATH + face.getFaceImagePath();
                    double similarity = FaceMatchUtil.compareFaces(
                            tempFilePath.toString(),
                            dbImagePath,
                            accessToken
                    );
                    
                    log.info("人脸对比结果 - 相似度: {}%, 数据库图片: {}", similarity, dbImagePath);
                    
                    if (similarity >= SIMILARITY_THRESHOLD) {
                        return true;
                    }
                }
            } finally {
                // 删除临时文件
                Files.deleteIfExists(tempFilePath);
            }
            
            return false;
        } catch (Exception e) {
            log.error("人脸验证过程发生错误", e);
            return false;
        }
    }
} 