package com.mafei.laboratory.commons.annotation;

import java.lang.annotation.*;

/**
 * @author wutangsheng
 * @create 2021-02-18 22:19
 * @info 自定义权限注解
 */

@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
    String roleKey() default "common";
}
