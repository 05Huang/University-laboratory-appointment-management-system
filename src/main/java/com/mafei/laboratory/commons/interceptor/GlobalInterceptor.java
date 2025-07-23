package com.mafei.laboratory.commons.interceptor;

import com.mafei.laboratory.commons.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wutangsheng
 * @create 2021-02-10 21:36
 * @info 全局拦截器，主要用于验证token的合法性
 */
@Component
public class GlobalInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证token,部分接口不拦截，例如登录接口等
        String token = "";
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.sendRedirect("/login");
            return false;
        }
        for (Cookie cookie : cookies) {
            if (!StringUtils.isEmpty(cookie.getName()) && "token".equalsIgnoreCase(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }
        if (isLogin(token)) {
            response.setStatus(HttpStatus.OK.value());
            return true;
        }
        //如果token不合法，就返回403状态，并重新将页面定向到登录页
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.sendRedirect("/login");
        return false;
    }

    /**
     * 判断是否登录
     *
     * @param token
     * @return
     */
    private boolean isLogin(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        try {
            JwtUtils.verifyToken(token);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
