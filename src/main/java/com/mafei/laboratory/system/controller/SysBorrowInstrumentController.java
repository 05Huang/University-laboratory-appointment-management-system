package com.mafei.laboratory.system.controller;

import com.mafei.laboratory.system.entity.vo.BorrowInstrumentVo;
import com.mafei.laboratory.system.service.SysBorrowInstrumentService;
import com.mafei.laboratory.system.service.dto.UpdateStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备借用表(SysBorrowInstrument)表控制层
 *
 * @author wts
 * @since 2021-03-07 08:48:26
 */
@RestController
@RequestMapping("/api/borrow/instrument")
@RequiredArgsConstructor
public class SysBorrowInstrumentController {
    private final SysBorrowInstrumentService instrumentService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<BorrowInstrumentVo> all = instrumentService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findAllByUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(instrumentService.findAllByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody BorrowInstrumentVo vo) {
        System.out.println(vo.getUserId());
        instrumentService.insert(vo);
        return ResponseEntity.ok("success");
    }

    @PatchMapping
    public ResponseEntity<Object> patchStatus(@RequestBody UpdateStatusDto json) {
        instrumentService.updateBorrow(json.getStatus(), json.getIds());
        return ResponseEntity.ok("success");
    }
}
