package com.mafei.laboratory.system.controller;


import com.mafei.laboratory.system.entity.vo.AllMenuVo;
import com.mafei.laboratory.system.entity.vo.MenuVo;
import com.mafei.laboratory.system.service.SysMenuService;
import com.mafei.laboratory.system.service.dto.AllMenuDto;
import com.mafei.laboratory.system.service.dto.UpdateStatusDto;
import com.mafei.laboratory.system.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限表(SysMenu)表控制层
 *
 * @author wutangsheng
 * @since 2021-02-18 11:07:01
 */
@RestController
@RequestMapping("/api/sysMenu")
@RequiredArgsConstructor
public class SysMenuController {
    private final SysMenuService menuService;


    @GetMapping
    public ResponseEntity<Object> getMenus() {
        return ResponseEntity.ok(menuService.getAllMenu());
    }

    @GetMapping(value = "/{roleId}")
    public ResponseEntity<Object> getMenus(@PathVariable Long roleId) {
        List<MenuVo> menuVos = menuService.queryMenuByRole(roleId);
        return ResponseEntity.ok(menuVos);
    }

    @GetMapping("/id")
    public ResponseEntity<Object> getMenu(Long id) {
        AllMenuVo allMenuVo = menuService.queryById(id);
        return ResponseEntity.ok(allMenuVo);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(menuService.deleteById(id));
    }

    @DeleteMapping
    public void deleteSome(@RequestBody Set<Long> ids) {
        menuService.deleteByIds(ids);
    }

    @PostMapping
    public ResponseEntity<Object> addOne(@Validated @RequestBody AllMenuVo menuVo) {
        menuService.insert(menuVo);
        return ResponseEntity.ok("success");
    }

    @PutMapping
    public ResponseEntity<Object> putOne(@RequestBody AllMenuDto menuDto) {
        menuService.update(menuDto);
        return ResponseEntity.ok("success");
    }

    @PatchMapping
    public void patchStatus(@RequestBody UpdateStatusDto json) {
        menuService.updateStatus(json.getStatus(), json.getIds());
    }
}
