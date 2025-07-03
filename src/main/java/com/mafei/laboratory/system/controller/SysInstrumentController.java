package com.mafei.laboratory.system.controller;

import com.mafei.laboratory.system.entity.vo.InstrumentVo;
import com.mafei.laboratory.system.entity.vo.LaboratoryVo;
import com.mafei.laboratory.system.service.SysInstrumentService;
import com.mafei.laboratory.system.service.dto.UpdateStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 仪器信息表(SysInstrument)表控制层
 *
 * @author wts
 * @since 2021-03-07 08:38:26
 */
@RestController
@RequestMapping("/api/instrument")
@RequiredArgsConstructor
public class SysInstrumentController {
    private final SysInstrumentService instrumentService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(instrumentService.findAll());
    }

    @GetMapping("/repair")
    public ResponseEntity<Object> findAllRepair() {
        return ResponseEntity.ok(instrumentService.findAllRepair());
    }

    @PostMapping
    public ResponseEntity<Object> addOne(@RequestBody InstrumentVo instrumentVo) {
        instrumentService.insert(instrumentVo);
        return ResponseEntity.ok("success");
    }

    @PutMapping
    public ResponseEntity<Object> putOne(@RequestBody InstrumentVo instrumentVo) {
        instrumentService.update(instrumentVo);
        return ResponseEntity.ok("success");
    }

    @PatchMapping
    public void patchStatus(@RequestBody UpdateStatusDto json) {
        instrumentService.updateStatus(json.getStatus(), json.getIds());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable("id") Long id) {
        instrumentService.deleteById(id);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping
    public void deleteSome(@RequestBody Set<Long> ids) {
        instrumentService.deleteByIds(ids);
    }
}
