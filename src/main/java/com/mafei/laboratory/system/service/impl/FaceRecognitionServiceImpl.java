package com.mafei.laboratory.system.service.impl;

import com.mafei.laboratory.commons.utils.FaceMatchUtil;
import com.mafei.laboratory.system.entity.SysUserFace;
import com.mafei.laboratory.system.repository.SysUserFaceRepository;
import com.mafei.laboratory.system.service.FaceRecognitionService;
import com.mafei.laboratory.system.vo.FaceVerificationVO;
import com.mafei.laboratory.system.repository.SysBorrowInstrumentRepository;
import com.mafei.laboratory.system.repository.SysBorrowLaboratoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.Arrays;

@Slf4j
@Service
public class FaceRecognitionServiceImpl implements FaceRecognitionService {

    @Resource
    private SysUserFaceRepository sysUserFaceRepository;

    @Resource
    private SysBorrowInstrumentRepository borrowInstrumentRepository;

    @Resource
    private SysBorrowLaboratoryRepository borrowLaboratoryRepository;

    @Value("${baidu.face.access-token}")
    private String accessToken;

    // 临时文件存储的绝对路径
    private static final String TEMP_FACE_PATH = "D:\\laboratory\\temp";
    
    // 人脸图片存储的绝对路径
    private static final String FACE_IMAGE_BASE_PATH = "D:\\laboratory\\face-images";
    
    // 人脸相似度阈值（80%）
    private static final double SIMILARITY_THRESHOLD = 80.0;

    // 有效的预约状态（审核通过、成功、使用中）
    private static final List<String> VALID_BORROW_STATUSES = Arrays.asList("0", "6", "7");

    @Override
    public FaceVerificationVO verifyFace(MultipartFile file) {
        log.info("========== 开始人脸识别流程 ==========");
        log.info("接收到的文件信息 - 名称: {}, 大小: {} bytes, 类型: {}", 
            file.getOriginalFilename(), file.getSize(), file.getContentType());

        try {
            // 确保临时目录存在
            File tempDir = new File(TEMP_FACE_PATH);
            if (!tempDir.exists()) {
                log.info("创建临时目录: {}", TEMP_FACE_PATH);
                tempDir.mkdirs();
            }

            // 使用时间戳和UUID生成唯一的文件名
            LocalDateTime now = LocalDateTime.now();
            String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String tempFileName = timestamp + "_" + UUID.randomUUID().toString() + ".jpg";
            Path tempFilePath = Paths.get(TEMP_FACE_PATH, tempFileName);
            
            // 保存上传的图片
            Files.write(tempFilePath, file.getBytes());
            log.info("临时人脸图片已保存 - 路径: {}, 大小: {} bytes", tempFilePath, Files.size(tempFilePath));

            // 获取所有启用状态的人脸记录
            List<SysUserFace> activeFaces = sysUserFaceRepository.findAllActiveFaces();
            log.info("获取到 {} 个活跃人脸记录用于比对", activeFaces.size());
            
            // 遍历比对所有启用的人脸
            int comparisonCount = 0;
            for (SysUserFace face : activeFaces) {
                comparisonCount++;
                // 构建完整的绝对路径
                String dbImagePath = Paths.get(FACE_IMAGE_BASE_PATH, face.getFaceImagePath().replace('/', '\\')).toString();
                
                log.info("正在进行第 {}/{} 次人脸比对", comparisonCount, activeFaces.size());
                log.info("比对信息 - 用户ID: {}, 临时文件: {}, 数据库图片: {}", 
                    face.getUserId(), tempFilePath, dbImagePath);
                
                double similarity = FaceMatchUtil.compareFaces(
                        tempFilePath.toString(),
                        dbImagePath,
                        accessToken
                );
                
                log.info("人脸对比结果 - 用户ID: {}, 相似度: {}%, 是否匹配: {}", 
                    face.getUserId(), similarity, similarity >= SIMILARITY_THRESHOLD);
                
                if (similarity >= SIMILARITY_THRESHOLD) {
                    log.info("找到匹配的人脸 - 用户ID: {}, 相似度: {}%", face.getUserId(), similarity);
                    
                    // 检查是否有有效的预约
                    boolean hasValidReservation = checkValidReservation(face.getUserId());
                    log.info("预约状态检查 - 用户ID: {}, 是否有效预约: {}", face.getUserId(), hasValidReservation);

                    if (!hasValidReservation) {
                        log.warn("用户无有效预约 - 用户ID: {}", face.getUserId());
                        return FaceVerificationVO.builder()
                            .verified(true)
                            .hasReservation(false)
                            .userId(face.getUserId())
                            .message("人脸识别成功，但未找到有效的预约记录")
                            .build();
                    }

                    log.info("验证完全通过 - 用户ID: {}", face.getUserId());
                    return FaceVerificationVO.builder()
                        .verified(true)
                        .hasReservation(true)
                        .userId(face.getUserId())
                        .message("验证成功")
                        .build();
                }
            }

            log.warn("未找到匹配的人脸 - 比对完成数: {}", comparisonCount);
            return FaceVerificationVO.builder()
                .verified(false)
                .hasReservation(false)
                .message("人脸识别失败，未找到匹配的用户")
                .build();

        } catch (Exception e) {
            log.error("人脸识别处理失败: ", e);
            return FaceVerificationVO.builder()
                .verified(false)
                .hasReservation(false)
                .message("人脸识别处理发生错误：" + e.getMessage())
                .build();
        } finally {
            log.info("========== 人脸识别流程结束 ==========\n");
        }
    }

    /**
     * 检查用户是否有有效的预约
     * @param userId 用户ID
     * @return 是否有有效预约
     */
    private boolean checkValidReservation(Long userId) {
        log.info("开始检查用户预约状态 - 用户ID: {}", userId);
        
        // 检查实验室预约
        boolean hasLabReservation = false;
        for (String status : VALID_BORROW_STATUSES) {
            if (borrowLaboratoryRepository.existsByUserIdAndBorrowStatus(userId, status)) {
                hasLabReservation = true;
                break;
            }
        }
        log.info("实验室预约检查结果 - 用户ID: {}, 是否有预约: {}", userId, hasLabReservation);

        // 检查仪器预约
        boolean hasInstrumentReservation = false;
        for (String status : VALID_BORROW_STATUSES) {
            if (borrowInstrumentRepository.existsByUserIdAndBorrowStatus(userId, status)) {
                hasInstrumentReservation = true;
                break;
            }
        }
        log.info("仪器预约检查结果 - 用户ID: {}, 是否有预约: {}", userId, hasInstrumentReservation);

        boolean hasAnyReservation = hasLabReservation || hasInstrumentReservation;
        log.info("预约状态检查完成 - 用户ID: {}, 最终结果: {}", userId, hasAnyReservation);
        
        return hasAnyReservation;
    }
} 