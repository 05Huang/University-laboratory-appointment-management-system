package com.mafei.laboratory.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mafei.laboratory.commons.exception.BadRequestException;
import com.mafei.laboratory.system.entity.SysUser;
import com.mafei.laboratory.system.entity.vo.ReceivingLog;
import com.mafei.laboratory.system.entity.vo.UserVo;
import com.mafei.laboratory.system.service.SysUserService;
import com.mafei.laboratory.system.service.dto.LoginDto;
import com.mafei.laboratory.system.service.dto.UpdateStatusDto;
import com.mafei.laboratory.system.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 用户信息表(SysUser)表控制层
 *
 * @author wutangsheng
 * @since 2021-02-18 11:08:00
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> selectOne(@PathVariable("id") Long id) {
        SysUser user = sysUserService.queryById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<UserVo> userVoList = sysUserService.queryAll();
        return ResponseEntity.ok(userVoList);
    }

    /**
     * 单个删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable("id") Long id) {
        boolean b = sysUserService.deleteById(id);
        if (b) {
            throw new BadRequestException("删除失败");
        }
        return ResponseEntity.ok("success");
    }

    @DeleteMapping
    public void deleteSome(@RequestBody Set<Long> ids) {
        sysUserService.deleteByIds(ids);
    }

    @PostMapping
    public ResponseEntity<Object> addOne(@Validated @RequestBody UserDto user) {
        sysUserService.insert(user);
        return ResponseEntity.ok("success");
    }

    @PutMapping
    public ResponseEntity<Object> putOne(@RequestBody UserDto userDto) {
        sysUserService.update(userDto);
        return ResponseEntity.ok("success");
    }

    @PatchMapping
    public void patchStatus(@RequestBody UpdateStatusDto json) {
        sysUserService.updateStatus(json.getStatus(), json.getIds());
    }

}
