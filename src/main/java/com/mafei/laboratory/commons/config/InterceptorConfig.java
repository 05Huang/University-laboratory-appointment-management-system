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
        addInterceptor.addPathPatterns("/**");
        addInterceptor.addPathPatterns("/api/**");
        addInterceptor.addPathPatterns("/index");

        // 排除配置
        addInterceptor.excludePathPatterns("/auth/**");
//        addInterceptor.excludePathPatterns("/api/**");
        addInterceptor.excludePathPatterns("/login");
        addInterceptor.excludePathPatterns("/logout");
        addInterceptor.excludePathPatterns("/register");
        addInterceptor.excludePathPatterns("/js/**");
        addInterceptor.excludePathPatterns("/css/**");
        addInterceptor.excludePathPatterns("/images/**");
        addInterceptor.excludePathPatterns("/fonts/**");
    }


}
