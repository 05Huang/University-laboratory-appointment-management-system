package com.mafei.laboratory.system.controller;

import com.mafei.laboratory.system.entity.vo.UseVo;
import com.mafei.laboratory.system.service.SysInstrumentUseService;
import com.mafei.laboratory.system.service.dto.UpdateStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 仪器用途表(SysInstrumentUse)表控制层
 *
 * @author wts
 * @since 2021-03-07 08:48:26
 */
@RestController
@RequestMapping("/api/use")
@RequiredArgsConstructor
public class SysInstrumentUseController {
    private final SysInstrumentUseService useService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(useService.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable("id") Long id) {
        useService.deleteById(id);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping
    public void deleteSome(@RequestBody Set<Long> ids) {
        useService.deleteByIds(ids);
    }

    @PostMapping
    public ResponseEntity<Object> addOne(@Validated @RequestBody UseVo useVo) {
        useService.insert(useVo);
        return ResponseEntity.ok("success");
    }

    @PutMapping
    public ResponseEntity<Object> putOne(@RequestBody UseVo useVo) {
        useService.update(useVo);
        return ResponseEntity.ok("success");
    }
}
