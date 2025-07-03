package com.mafei.laboratory.commons.utils;

import com.mafei.laboratory.commons.exception.BadRequestException;
import com.mafei.laboratory.commons.security.TokenProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
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
     *
     * @param token
     * @return
     */
    public static Map<String, Object> parseToken(String token) {
        Jws<Claims> jws = null;
        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(token);

        } catch (JwtException e) {
            throw new BadRequestException(HttpStatus.FORBIDDEN, "token失效，请重新登录");
        }
        return jws.getBody();
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    public static boolean verifyToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            e.printStackTrace();
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
