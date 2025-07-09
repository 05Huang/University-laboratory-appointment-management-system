package com.mafei.laboratory.system.controller;

import com.mafei.laboratory.commons.enums.StatusEnum;
import com.mafei.laboratory.system.service.SysBorrowInstrumentService;
import com.mafei.laboratory.system.service.SysBorrowLaboratoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    
    private final SysBorrowLaboratoryService laboratoryService;
    private final SysBorrowInstrumentService instrumentService;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // 计算各种统计数据
        long count = laboratoryService.countAll() + instrumentService.countAll();
        // 使用中的数量（状态码 7）
        long countStatus = laboratoryService.countAllByStatus(StatusEnum.BORROW.getStatus()) 
                        + instrumentService.countAllByStatus(StatusEnum.BORROW.getStatus());
        // 审核中的数量（状态码 5）
        long borrowApplyCount = laboratoryService.countAllByStatus(StatusEnum.CHECK.getStatus()) 
                             + instrumentService.countAllByStatus(StatusEnum.CHECK.getStatus());
        // 维修中的数量（状态码 2）
        long repairCount = laboratoryService.countAllByStatus(StatusEnum.REPAIR.getStatus()) 
                        + instrumentService.countAllByStatus(StatusEnum.REPAIR.getStatus());
        
        // 添加到返回结果
        stats.put("countAll", count);
        stats.put("countStatus", countStatus);
        stats.put("borrowApplyCount", borrowApplyCount);
        stats.put("reserveCount", repairCount);
        
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/teacher/{userId}")
    public ResponseEntity<Map<String, Object>> getTeacherStats(@PathVariable("userId") Long userId) {
        Map<String, Object> stats = new HashMap<>();
        
        // 计算教师个人的统计数据
        long count = laboratoryService.countAllByUserId(userId) + instrumentService.countAllByUserId(userId);
        // 未归还的数量（状态码 7）
        long countStatus = laboratoryService.countAllByUserIdAndStatus(userId, StatusEnum.BORROW.getStatus()) 
                        + instrumentService.countAllByUserIdAndStatus(userId, StatusEnum.BORROW.getStatus());
        // 总预约实验室数量
        long totalLabs = laboratoryService.countAllByUserId(userId);
        // 未归还实验室数量
        long labsNotReturned = laboratoryService.countAllByUserIdAndStatus(userId, StatusEnum.BORROW.getStatus());
        
        // 添加到返回结果
        stats.put("countAll", count);
        stats.put("countStatus", countStatus);
        stats.put("borrowApplyCount", totalLabs);
        stats.put("reserveCount", labsNotReturned);
        
        return ResponseEntity.ok(stats);
    }
} 