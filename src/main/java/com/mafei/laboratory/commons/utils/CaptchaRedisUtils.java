package com.mafei.laboratory.commons.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CaptchaRedisUtils {

    private static final String CAPTCHA_PREFIX = "CAPTCHA:";
    private static final long CAPTCHA_EXPIRE_SECONDS = 30;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 保存验证码
     * @param uuid 唯一标识
     * @param code 验证码
     */
    public void saveCaptcha(String uuid, String code) {
        String key = CAPTCHA_PREFIX + uuid;
        redisTemplate.opsForValue().set(key, code, CAPTCHA_EXPIRE_SECONDS, TimeUnit.SECONDS);
        log.debug("保存验证码到Redis - key: {}, code: {}", key, code);
    }

    /**
     * 获取验证码
     * @param uuid 唯一标识
     * @return 验证码
     */
    public String getCaptcha(String uuid) {
        String key = CAPTCHA_PREFIX + uuid;
        Object value = redisTemplate.opsForValue().get(key);
        log.debug("从Redis获取验证码 - key: {}, value: {}", key, value);
        return value != null ? value.toString() : null;
    }

    /**
     * 验证验证码
     * @param uuid 唯一标识
     * @param code 用户输入的验证码
     * @return 是否正确
     */
    public boolean validateCaptcha(String uuid, String code) {
        if (uuid == null || code == null) {
            log.warn("验证码验证失败：uuid或code为空");
            return false;
        }
        
        String key = CAPTCHA_PREFIX + uuid;
        Object value = redisTemplate.opsForValue().get(key);
        log.debug("验证验证码 - key: {}, expected: {}, actual: {}", key, value, code);
        
        if (value != null && code.equalsIgnoreCase(value.toString())) {
            // 验证成功后删除验证码
            redisTemplate.delete(key);
            log.debug("验证码验证成功，已删除Redis中的验证码 - key: {}", key);
            return true;
        }
        
        log.warn("验证码验证失败 - key: {}", key);
        return false;
    }

    /**
     * 删除验证码
     * @param uuid 唯一标识
     */
    public void deleteCaptcha(String uuid) {
        String key = CAPTCHA_PREFIX + uuid;
        redisTemplate.delete(key);
        log.debug("删除Redis中的验证码 - key: {}", key);
    }
} 