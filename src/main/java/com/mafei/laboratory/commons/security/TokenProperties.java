package com.mafei.laboratory.commons.security;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wutangsheng
 * @create 2021-02-18 11:40
 * @info
 */
@Data
@ConfigurationProperties(prefix = "jwt")
public class TokenProperties implements InitializingBean {
    /**
     * Request Headers ： Authorization
     */
    private String header;

    /**
     * 令牌前缀，最后留个空格 Bearer
     */
    private String tokenStartWith;

    /**
     * 必须使用最少88位的Base64对该令牌进行编码
     */
    private String base64Secret;

    /**
     * 令牌过期时间 此处单位/毫秒
     */
    private Long tokenValidityInSeconds;

    /**
     * token 续期检查
     */
    private Long detect;

    /**
     * 续期时间
     */
    private Long renew;

    public static String HEADER;
    public static String TOKEN_START_WITH;
    public static String BASE64SECRET;
    public static Long TOKEN_VALIDITY_IN_SECONDS;
    public static Long DETECT;
    public static Long RENEW;


    @Override
    public void afterPropertiesSet() throws Exception {
        HEADER = header;
        TOKEN_START_WITH = tokenStartWith + " ";
        BASE64SECRET = base64Secret;
        TOKEN_VALIDITY_IN_SECONDS = tokenValidityInSeconds;
        DETECT = detect;
        RENEW = renew;
    }
}
