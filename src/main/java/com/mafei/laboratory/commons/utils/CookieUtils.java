package com.mafei.laboratory.commons.utils;

import com.alibaba.fastjson.JSON;
import com.mafei.laboratory.system.entity.SysMenu;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wutangsheng
 * @create 2021-02-18 22:52
 * @info 把菜单保存到cookie里
 */
public class CookieUtils {

    /**
     * 验证“token cookie”是否存在
     *
     * @param cookies
     * @return
     */
    public static boolean cookieNotExist(Cookie[] cookies) {
        if (cookies == null) {
            return true;
        }
        if (cookies.length == 0) {
            return true;
        }
        for (Cookie cookie : cookies) {
            if (!StringUtils.isEmpty(cookie.getName()) && "token".equalsIgnoreCase(cookie.getName())) {
                return true;
            }
        }
        return false;
    }

    public static Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie c = null;
        for (Cookie cookie : cookies) {
            if (!StringUtils.isEmpty(cookie.getName()) && "token".equalsIgnoreCase(cookie.getName())) {
                c = cookie;
            }
        }
        return c;
    }

    /**
     * 清除cookie
     */
    public static void removeCookie(Cookie cookie) {
        if (cookie == null) {
            return;
        }
        cookie.setMaxAge(0);

    }

    private static boolean tokenExist(Cookie cookie) {
        return !StringUtils.isEmpty(cookie.getName()) && "token".equalsIgnoreCase(cookie.getName());
    }

}
