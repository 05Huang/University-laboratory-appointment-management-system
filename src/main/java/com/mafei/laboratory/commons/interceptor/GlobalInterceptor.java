package com.mafei.laboratory.commons.interceptor;

import com.mafei.laboratory.commons.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class GlobalInterceptor implements HandlerInterceptor {
    
    private static final String LOGIN_PATH = "/login";
    private static final String TOKEN_COOKIE = "token";
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        
        // 如果没有cookie，重定向到登录页
        if (cookies == null || cookies.length == 0) {
            log.debug("未找到Cookie，重定向到登录页");
            redirectToLogin(response);
            return false;
        }
        
        // 查找token cookie
        String token = findTokenCookie(cookies);
        if (StringUtils.isEmpty(token)) {
            log.debug("未找到token cookie，重定向到登录页");
            redirectToLogin(response);
            return false;
        }
        
        // 验证token
        if (isValidToken(token)) {
            response.setStatus(HttpStatus.OK.value());
            return true;
        }
        
        // token无效，重定向到登录页
        log.debug("Token无效，重定向到登录页");
        redirectToLogin(response);
        return false;
    }
    
    /**
     * 查找token cookie
     */
    private String findTokenCookie(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (TOKEN_COOKIE.equalsIgnoreCase(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
    
    /**
     * 验证token是否有效
     */
    private boolean isValidToken(String token) {
        try {
            return JwtUtils.verifyToken(token);
        } catch (Exception e) {
            // 所有token相关异常都已在JwtUtils中处理，这里不需要再记录日志
            return false;
        }
    }
    
    /**
     * 重定向到登录页
     */
    private void redirectToLogin(HttpServletResponse response) throws Exception {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.sendRedirect(LOGIN_PATH);
    }
}
