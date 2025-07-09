package com.mafei.laboratory.system.controller;

import com.mafei.laboratory.commons.exception.BadRequestException;
import com.mafei.laboratory.commons.utils.JwtUtils;
import com.mafei.laboratory.commons.utils.CaptchaRedisUtils;
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
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
    private final CaptchaRedisUtils captchaRedisUtils;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Validated @RequestBody LoginDto authUser, HttpServletRequest request) throws Exception {
        String code = authUser.getCaptcha();
        String uuid = authUser.getUuid();
        
        // 判断验证码
        if (StringUtils.isBlank(code) || StringUtils.isBlank(uuid)) {
            throw new BadRequestException("验证码不存在或已过期");
        }
        
        // 验证验证码
        if (!captchaRedisUtils.validateCaptcha(uuid, code)) {
            throw new BadRequestException("验证码错误或已过期");
        }

        // 验证数据库里的账号密码是否正确
        LoginUserVo user = userService.queryByUsername(authUser);
        // 构造token所需要的数据
        Map<String, Object> map = new HashMap<>(4);
        map.put("userId", user.getUserId());
        map.put("roleId", user.getRoleId());

        String token = JwtUtils.createToken(map);
        //返回菜单
        return ResponseEntity.ok(token);
    }

    /**
     * 获取验证码的UUID
     */
    @GetMapping("/captcha/uuid")
    public ResponseEntity<Object> getCaptchaUuid() {
        // 生成UUID
        String uuid = UUID.randomUUID().toString();
        
        // 生成验证码
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(120, 38);
        String verCode = captcha.text().toLowerCase();
        
        // 将验证码存入Redis
        captchaRedisUtils.saveCaptcha(uuid, verCode);
        
        // 返回UUID
        Map<String, String> result = new HashMap<>(1);
        result.put("uuid", uuid);
        return ResponseEntity.ok(result);
    }

    /**
     * 获取验证码图片
     */
    @GetMapping("/captcha/image/{uuid}")
    public void getCaptchaImage(@PathVariable String uuid, HttpServletResponse response) throws Exception {
        try {
            // 生成验证码
            ArithmeticCaptcha captcha = new ArithmeticCaptcha(120, 38);
            String verCode = captcha.text().toLowerCase();
            
            // 更新Redis中的验证码
            captchaRedisUtils.saveCaptcha(uuid, verCode);
            
            // 设置响应头
            response.setContentType("image/jpeg");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache, no-store");
            response.setDateHeader("Expires", 0);
            
            // 输出图片
            try (ServletOutputStream out = response.getOutputStream()) {
                captcha.out(out);
                out.flush();
            } catch (IOException e) {
                // 客户端中断连接的异常，可以忽略
                if (!(e instanceof ClientAbortException)) {
                    throw e;
                }
            }
        } catch (Exception e) {
            // 其他异常需要处理
            if (!(e.getCause() instanceof ClientAbortException)) {
                throw e;
            }
        }
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
