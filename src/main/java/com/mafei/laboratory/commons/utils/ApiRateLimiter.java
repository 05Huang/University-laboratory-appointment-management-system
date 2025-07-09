package com.mafei.laboratory.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * API调用频率限制工具类
 */
public class ApiRateLimiter {
    private static final Logger logger = LoggerFactory.getLogger(ApiRateLimiter.class);
    private static long lastCallTime = 0;
    private static final Object lock = new Object();
    
    /**
     * 确保两次API调用之间的间隔至少为1秒
     */
    public static void limitApiCall() {
        synchronized (lock) {
            long currentTime = System.currentTimeMillis();
            long timeSinceLastCall = currentTime - lastCallTime;
            
            if (timeSinceLastCall < 1000) {
                try {
                    long sleepTime = 1000 - timeSinceLastCall;
                    logger.debug("API调用限流，休眠{}毫秒", sleepTime);
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("API限流休眠被中断", e);
                }
            }
            
            lastCallTime = System.currentTimeMillis();
        }
    }
} 