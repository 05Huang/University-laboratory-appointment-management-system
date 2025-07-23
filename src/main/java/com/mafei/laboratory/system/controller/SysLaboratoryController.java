package com.mafei.laboratory.system.controller;

import com.mafei.laboratory.system.entity.SysLaboratory;
import com.mafei.laboratory.system.entity.vo.LaboratoryVo;
import com.mafei.laboratory.system.entity.vo.RoleVo;
import com.mafei.laboratory.system.service.SysLaboratoryService;
import com.mafei.laboratory.system.service.dto.UpdateStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 实验室信息表(SysLaboratory)表控制层
 *
 * @author wutangsheng
 * @since 2021-02-24 10:39:22
 */
@RestController
@RequestMapping("/api/laboratory")
@RequiredArgsConstructor
public class SysLaboratoryController {

    private final SysLaboratoryService laboratoryService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(laboratoryService.findAll());
    }

    @GetMapping("/status")
    public ResponseEntity<Object> getAllByStatus() {
        return ResponseEntity.ok(laboratoryService.findAllByStatus());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable("id") Long id) {
        laboratoryService.deleteById(id);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping
    public void deleteSome(@RequestBody Set<Long> ids) {
        laboratoryService.deleteByIds(ids);
    }

    @PostMapping
    public ResponseEntity<Object> addOne(@Validated @RequestBody LaboratoryVo laboratoryVo) {
        laboratoryService.insert(laboratoryVo);
        return ResponseEntity.ok("success");
    }

    @PutMapping
    public ResponseEntity<Object> putOne(@RequestBody LaboratoryVo laboratoryVo) {
        laboratoryService.update(laboratoryVo);
        return ResponseEntity.ok("success");
    }

    @PatchMapping
    public void patchStatus(@RequestBody UpdateStatusDto json) {
        laboratoryService.updateStatus(json.getStatus(), json.getIds());
    }

}
