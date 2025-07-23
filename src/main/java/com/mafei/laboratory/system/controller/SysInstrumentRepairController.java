package com.mafei.laboratory.system.controller;

import com.mafei.laboratory.system.entity.vo.RepairVo;
import com.mafei.laboratory.system.service.SysInstrumentRepairService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

/**
 * 仪器用途表(SysInstrumentRepair)表控制层
 *
 * @author wts
 * @since 2021-03-07 08:48:26
 */
@RestController
@RequestMapping("/api/repair")
@RequiredArgsConstructor
public class SysInstrumentRepairController {

    private final SysInstrumentRepairService repairService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(repairService.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable("id") Long id) {
        repairService.deleteById(id);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping
    public void deleteSome(@RequestBody Set<Long> ids) {
        repairService.deleteByIds(ids);
    }

    @PostMapping
    public ResponseEntity<Object> addOne(@Validated @RequestBody RepairVo repairVo) {
        repairService.insert(repairVo);
        return ResponseEntity.ok("success");
    }

    @PutMapping
    public ResponseEntity<Object> putOne(@RequestBody RepairVo repairVo) {
        repairService.update(repairVo);
        return ResponseEntity.ok("success");
    }
}
