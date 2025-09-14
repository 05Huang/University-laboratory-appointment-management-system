package com.mafei.laboratory.commons.config;

import com.mafei.laboratory.commons.interceptor.GlobalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wutangsheng
 * @create 2021-02-10 21:43
 * @info 处理拦截规则
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private GlobalInterceptor globalInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(globalInterceptor);
        
        // 拦截所有请求
        addInterceptor.addPathPatterns("/**");
        addInterceptor.addPathPatterns("/api/**");
        addInterceptor.addPathPatterns("/index");

        // 排除配置
        addInterceptor.excludePathPatterns(
            "/auth/**",           // 认证相关
            "/login",            // 登录页面
            "/logout",           // 登出
            "/register",         // 注册
            "/api/face-recognition/**",  // 人脸识别API
            "/access/**",        // 门禁相关
            "/js/**",           // 静态资源
            "/css/**",
            "/images/**",
            "/fonts/**"
        );
    }
}
