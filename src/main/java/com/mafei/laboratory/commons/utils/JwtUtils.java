package com.mafei.laboratory.commons.utils;

import com.mafei.laboratory.commons.exception.BadRequestException;
import com.mafei.laboratory.commons.security.TokenProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author wutangsheng
 * @create 2021-02-10 21:32
 * @info JWT token jjwt
 */
@Slf4j
public class JwtUtils {
    /**
     * 创建token
     *
     * @param claimMap
     * @return
     */
    public static String createToken(Map<String, Object> claimMap) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                // 设置签发时间
                .setIssuedAt(new Date(currentTimeMillis))
                // 设置过期时间
                .setExpiration(new Date(currentTimeMillis + TokenProperties.TOKEN_VALIDITY_IN_SECONDS))
                .addClaims(claimMap)
                .setSubject((String) claimMap.get("user"))
                .signWith(generateKey())
                .compact();
    }

    /**
     * token 解析
     * @param token JWT token
     * @return Claims
     */
    public static Map<String, Object> parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.debug("Token已过期: {}", e.getMessage());
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "登录已过期，请重新登录");
        } catch (JwtException e) {
            log.debug("Token解析失败: {}", e.getMessage());
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "登录状态无效，请重新登录");
        } catch (Exception e) {
            log.error("Token解析过程发生未知错误", e);
            throw new BadRequestException(HttpStatus.INTERNAL_SERVER_ERROR, "服务器内部错误，请稍后重试");
        }
    }

    /**
     * 验证token
     * @param token JWT token
     * @return 是否有效
     */
    public static boolean verifyToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            // Token过期，不记录堆栈
            log.debug("Token已过期: {}", e.getMessage());
            return false;
        } catch (JwtException e) {
            // 其他JWT相关异常，不记录堆栈
            log.debug("Token验证失败: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            // 其他未预期的异常，记录堆栈
            log.error("Token验证过程发生未知错误", e);
            return false;
        }
    }

    /**
     * 生成安全密钥
     *
     * @return
     */
    public static Key generateKey() {
        return new SecretKeySpec(
                Decoders.BASE64.decode(TokenProperties.BASE64SECRET),
                SignatureAlgorithm.HS256.getJcaName());
    }

    /**
     * 根据请求头获取token
     *
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        final String requestHeader = request.getHeader(TokenProperties.HEADER);
        if (requestHeader != null && requestHeader.startsWith(TokenProperties.TOKEN_START_WITH)) {
            return requestHeader.substring(7);
        }
        return null;
    }
}
