package com.mafei.laboratory.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mafei.laboratory.commons.exception.BadRequestException;
import com.mafei.laboratory.commons.utils.CookieUtils;
import com.mafei.laboratory.commons.utils.JwtUtils;
import com.mafei.laboratory.system.entity.SysUser;
import com.mafei.laboratory.system.entity.vo.ReceivingLog;
import com.mafei.laboratory.system.entity.vo.UserVo;
import com.mafei.laboratory.system.service.SysUserService;
import com.mafei.laboratory.system.service.dto.LoginDto;
import com.mafei.laboratory.system.service.dto.UpdateStatusDto;
import com.mafei.laboratory.system.service.dto.UserDto;
import com.mafei.laboratory.system.service.dto.ChangePwdDto;
import com.mafei.laboratory.system.service.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
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

    /**
     * 修改密码
     */
    @PostMapping("/changePwd")
    public ResponseEntity<Object> changePassword(@Validated @RequestBody ChangePwdDto changePwdDto, HttpServletRequest request) {
        // 从Cookie中获取用户ID
        Cookie cookie = CookieUtils.getCookie(request);
        if (cookie == null) {
            throw new BadRequestException("未登录或登录已过期");
        }
        String token = cookie.getValue();
        Map<String, Object> map = JwtUtils.parseToken(token);
        Long userId = Long.valueOf(String.valueOf(map.get("userId")));

        // 调用service修改密码
        sysUserService.updatePassword(userId, changePwdDto);
        return ResponseEntity.ok("密码修改成功");
    }

    /**
     * 更新个人信息
     */
    @PostMapping("/updateInfo")
    public ResponseEntity<Object> updateUserInfo(@Validated @RequestBody UserInfoDto userInfoDto, HttpServletRequest request) {
        // 从Cookie中获取用户ID
        Cookie cookie = CookieUtils.getCookie(request);
        if (cookie == null) {
            throw new BadRequestException("未登录或登录已过期");
        }
        String token = cookie.getValue();
        Map<String, Object> map = JwtUtils.parseToken(token);
        Long userId = Long.valueOf(String.valueOf(map.get("userId")));

        // 调用service更新个人信息
        sysUserService.updateUserInfo(userId, userInfoDto);
        return ResponseEntity.ok("个人信息更新成功");
    }

}
