package com.mafei.laboratory.system.controller;

import com.mafei.laboratory.system.entity.vo.BorrowInstrumentVo;
import com.mafei.laboratory.system.entity.vo.BorrowLaboratoryVo;
import com.mafei.laboratory.system.service.SysBorrowLaboratoryService;
import com.mafei.laboratory.system.service.dto.UpdateStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 设备借用表(SysBorrowLaboratory)表控制层
 *
 * @author wts
 * @since 2021-03-07 08:28:26
 */
@RestController
@RequestMapping("/api/borrow/laboratory")
@RequiredArgsConstructor
public class SysBorrowLaboratoryController {
    private final SysBorrowLaboratoryService laboratoryService;


    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<BorrowLaboratoryVo> all = laboratoryService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/review")
    public ResponseEntity<Object> findAllReview() {
        List<BorrowLaboratoryVo> all = laboratoryService.findAllReview();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findAllByUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(laboratoryService.findAllByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody BorrowLaboratoryVo vo) {
        laboratoryService.insert(vo);
        return ResponseEntity.ok("success");
    }

    @PatchMapping
    public void patchStatus(@RequestBody UpdateStatusDto json) {
        laboratoryService.updateBorrow(json.getStatus(), json.getIds());
    }
}
