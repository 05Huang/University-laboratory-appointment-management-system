package com.mafei.laboratory.page.controller;

import com.mafei.laboratory.commons.exception.BadRequestException;
import com.mafei.laboratory.commons.utils.CookieUtils;
import com.mafei.laboratory.commons.utils.JwtUtils;
import com.mafei.laboratory.system.entity.SysUser;
import com.mafei.laboratory.system.service.SysBorrowInstrumentService;
import com.mafei.laboratory.system.service.SysBorrowLaboratoryService;
import com.mafei.laboratory.system.service.SysMenuService;
import com.mafei.laboratory.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author wutangsheng
 * @create 2021-02-10 1:16
 * @info 管理常用页面，首页 404等
 */
@Controller
@RequiredArgsConstructor
public class PageController {

    private final SysMenuService menuService;
    private final SysUserService userService;
    private final SysBorrowLaboratoryService laboratoryService;
    private final SysBorrowInstrumentService instrumentService;

    /**
     * @return 主页
     */
    @GetMapping("/index")
    public String index(HttpServletRequest request, Model model) {
        Cookie cookie = CookieUtils.getCookie(request);
        if (cookie == null) {
            throw new BadRequestException(HttpStatus.FORBIDDEN, "token失效，请重新登录");
        }
        String token = cookie.getValue();
        Map<String, Object> map = JwtUtils.parseToken(token);
        Long roleId = Long.valueOf(String.valueOf(map.get("roleId")));
        Long userId = Long.valueOf(String.valueOf(map.get("userId")));
        SysUser user = userService.queryById(userId);
        model.addAttribute("avatar", user.getAvatar());
        model.addAttribute("username", user.getUserName() + "<span class=\"caret\"></span>");
        model.addAttribute("roleId", roleId);
        model.addAttribute("menus", menuService.queryMenuByRole(roleId));
        return "index";
    }

    /**
     * 登录页
     *
     * @return
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        //否则就可以返回
        return "login";
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Cookie cookie = CookieUtils.getCookie(request);
        CookieUtils.removeCookie(cookie);
        return "login";
    }

    /**
     * @return 404
     */
    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/main")
    public String main(HttpServletRequest request, Model model) {
        Cookie cookie = CookieUtils.getCookie(request);
        if (cookie == null) {
            throw new BadRequestException(HttpStatus.FORBIDDEN, "token失效，请重新登录");
        }

        long count = laboratoryService.countAll() + instrumentService.countAll();
        long countStatus = laboratoryService.countAllByStatus("7") + instrumentService.countAllByStatus("7");
        long countApply = laboratoryService.countAllByStatus("5") + instrumentService.countAllByStatus("5");

        String token = cookie.getValue();
        Map<String, Object> map = JwtUtils.parseToken(token);
        Long userId = Long.valueOf(String.valueOf(map.get("userId")));
        long roleId = Long.parseLong(String.valueOf(map.get("roleId")));
        model.addAttribute("userId", userId);
        model.addAttribute("countAll", count);
        model.addAttribute("countStatus", countStatus);
        model.addAttribute("countApply", countApply);


        if (roleId == 2) {
            return "teacher_main";
        } else if (roleId == 3) {
            return "student_main";
        }
        return "lyear_main";
    }
}
