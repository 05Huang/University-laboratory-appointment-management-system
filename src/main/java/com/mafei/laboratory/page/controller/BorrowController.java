package com.mafei.laboratory.page.controller;

import com.mafei.laboratory.commons.utils.CookieUtils;
import com.mafei.laboratory.commons.utils.JwtUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author wutangsheng
 * @create 2021-03-10 0:41
 * @info
 */
@Controller
@RequestMapping("/borrow")
public class BorrowController {
    private final String prefix = "borrow";

    @GetMapping("/apparatus")
    public String borrowApparatus(HttpServletRequest request, Model model) {
        model.addAttribute("userId", getUserId(request));
        return prefix + "/apparatus";
    }

    @GetMapping("/laboratory")
    public String borrowLog(HttpServletRequest request, Model model) {
        model.addAttribute("userId", getUserId(request));
        return prefix + "/laboratory";
    }


    private Long getUserId(HttpServletRequest request) {
        Cookie cookie = CookieUtils.getCookie(request);
        String token = cookie.getValue();
        Map<String, Object> map = JwtUtils.parseToken(token);
        return Long.valueOf(String.valueOf(map.get("userId")));
    }

}
