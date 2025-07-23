package com.mafei.laboratory.system.controller;


import com.mafei.laboratory.system.entity.vo.PositionVo;
import com.mafei.laboratory.system.service.SysInstrumentPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 仪器位置表(SysInstrumentPosition)表控制层
 *
 * @author wts
 * @since 2021-03-07 08:48:26
 */
@RestController
@RequestMapping("/api/position")
@RequiredArgsConstructor
public class SysInstrumentPositionController {
    private final SysInstrumentPositionService positionService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(positionService.findAll());
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable("id") Long id) {
        positionService.deleteById(id);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping
    public void deleteSome(@RequestBody Set<Long> ids) {
        positionService.deleteByIds(ids);
    }

    @PostMapping
    public ResponseEntity<Object> addOne(@Validated @RequestBody PositionVo positionVo) {
        positionService.insert(positionVo);
        return ResponseEntity.ok("success");
    }

    @PutMapping
    public ResponseEntity<Object> putOne(@RequestBody PositionVo positionVo) {
        positionService.update(positionVo);
        return ResponseEntity.ok("success");
    }
}
