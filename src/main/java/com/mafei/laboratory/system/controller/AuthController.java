package com.mafei.laboratory.system.controller;

import com.mafei.laboratory.commons.exception.BadRequestException;
import com.mafei.laboratory.commons.utils.JwtUtils;
import com.mafei.laboratory.system.entity.vo.LoginUserVo;
import com.mafei.laboratory.system.service.SysBorrowInstrumentService;
import com.mafei.laboratory.system.service.SysBorrowLaboratoryService;
import com.mafei.laboratory.system.service.SysUserService;
import com.mafei.laboratory.system.service.dto.LoginDto;
import com.mafei.laboratory.system.service.dto.UpdateDto;
import com.mafei.laboratory.system.service.dto.UpdateStatusDto;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wutangsheng
 * @create 2021-02-17 17:30
 * @info
 */

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final SysUserService userService;
    private final SysBorrowInstrumentService instrumentService;
    private final SysBorrowLaboratoryService laboratoryService;

    private String verCode;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Validated @RequestBody LoginDto authUser, HttpServletRequest request) throws Exception {
        String code = authUser.getCaptcha();
        // 判断验证码
        if (StringUtils.isBlank(code) || StringUtils.isEmpty(code)) {
            throw new BadRequestException("验证码不存在或已过期");
        }
        if (!StringUtils.isEmpty(verCode) && !verCode.equals(code)) {
            // 清除session中的验证码
            CaptchaUtil.clear(request);
            throw new BadRequestException("验证码错误");
        }
        // 验证数据库里的账号密码是否正确
        LoginUserVo user = userService.queryByUsername(authUser);
        // todo 构造token所需要的数据
        Map<String, Object> map = new HashMap<>(4);
        map.put("userId", user.getUserId());
        map.put("roleId", user.getRoleId());

        String token = JwtUtils.createToken(map);
        //返回菜单
        return ResponseEntity.ok(token);
    }

    /**
     * 图形验证码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(120, 38);
        this.verCode = captcha.text().toLowerCase();
        CaptchaUtil.out(captcha, request, response);
//        // 换成不需要 ScriptEngine 的验证码类型（推荐）
//        SpecCaptcha captcha = new SpecCaptcha(120, 38, 4); // 4个字符的字母数字验证码
//        this.verCode = captcha.text().toLowerCase();
//        CaptchaUtil.out(captcha, request, response);
    }

    @PatchMapping("/instrument/{id}")
    public ResponseEntity<Object> checkInstrument(@RequestBody UpdateDto updateDto) {
        instrumentService.updateCheck(updateDto);
        return ResponseEntity.ok("success");
    }

    @PatchMapping("/instrument")
    public ResponseEntity<Object> checkInstrument(String status, Set<Long> ids) {
        instrumentService.updateCheck(status, ids);
        return ResponseEntity.ok("success");
    }

    @PatchMapping("/laboratory/{id}")
    public ResponseEntity<Object> checkLaboratory(@RequestBody UpdateDto updateDto) {
        laboratoryService.updateCheck(updateDto);
        return ResponseEntity.ok("success");
    }

    @PatchMapping("/laboratory")
    public ResponseEntity<Object> checkLaboratory(String status, Set<Long> ids) {
        laboratoryService.updateCheck(status, ids);
        return ResponseEntity.ok("success");
    }

}
