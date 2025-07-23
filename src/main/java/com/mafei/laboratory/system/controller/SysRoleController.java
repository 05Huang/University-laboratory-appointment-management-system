package com.mafei.laboratory.system.controller;

import com.mafei.laboratory.system.entity.SysRole;
import com.mafei.laboratory.system.entity.vo.AllMenuVo;
import com.mafei.laboratory.system.entity.vo.MenuVo;
import com.mafei.laboratory.system.entity.vo.RoleVo;
import com.mafei.laboratory.system.service.SysRoleService;
import com.mafei.laboratory.system.service.dto.AllMenuDto;
import com.mafei.laboratory.system.service.dto.UpdateStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 角色信息表(SysRole)表控制层
 *
 * @author wutangsheng
 * @since 2021-02-18 11:08:00
 */
@RestController
@RequestMapping("/api/sysRole")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService roleService;

    @GetMapping
    public ResponseEntity<Object> getRoles() {
        return ResponseEntity.ok(roleService.queryAll());
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getMenu(@PathVariable Long id) {
        RoleVo roleVo = roleService.queryById(id);
        return ResponseEntity.ok(roleVo);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roleService.deleteById(id));
    }

    @DeleteMapping
    public void deleteSome(@RequestBody Set<Long> ids) {
        roleService.deleteByIds(ids);
    }

    @PostMapping
    public ResponseEntity<Object> addOne(@Validated @RequestBody RoleVo roleVo) {
        roleService.insert(roleVo);
        return ResponseEntity.ok("success");
    }

    @PutMapping
    public ResponseEntity<Object> putOne(@RequestBody RoleVo roleVo) {
        roleService.update(roleVo);
        return ResponseEntity.ok("success");
    }

    @PatchMapping
    public void patchStatus(@RequestBody UpdateStatusDto json) {
        roleService.updateStatus(json.getStatus(), json.getIds());
    }
}
