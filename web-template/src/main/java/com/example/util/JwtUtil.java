package com.example.util;

import java.util.Date;
import java.util.UUID;

import cn.hutool.core.lang.Assert;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WuQinglong
 */
@Slf4j
public class JwtUtil {

    /**
     * 秘钥
     */
    private static final String SIGNING_KEY = "WEwxCubMAm5KWKgd1Iq";

    /**
     * Token 超时时间
     */
    private static final long EXPIRE_TIME = 1000L * 60 * 60 * 24;

    /**
     * 签发者
     */
    private static final String ISSUER = "none";

    /**
     * Token 在 header 中的 name
     */
    private static final String TOKEN_NAME = "token";

    /**
     * 生成token
     *
     * @param userId   用户的唯一id
     * @param username 用户名
     * @return jwt-token
     */
    public static String createToken(String userId, String username) {
        JwtBuilder builder = Jwts.builder()
            .setHeaderParam("alg", "HS256")
            .setHeaderParam("typ", "JWT")
            .setId(UUID.randomUUID().toString())
            .setIssuer(ISSUER)
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
            .claim("userId", userId)
            .claim("username", username)
            .signWith(SignatureAlgorithm.HS256, SIGNING_KEY.getBytes());
        return builder.compact();
    }

    /**
     * 获取 Token
     */
    public static String getToken() {
        Object jwtToken = SpringMVCUtil.getRequest().getHeader(TOKEN_NAME);
        Assert.notNull(jwtToken, "请求头中缺少token信息");
        return String.valueOf(jwtToken);
    }

    /**
     * 校验 header 中的 token 是否有效
     * 如果 token 非有效，则会抛出异常
     */
    public static void validToken() {
        getClaims(getToken());
    }

    /**
     * 从一个jwt里面解析出Claims
     *
     * @param token token值
     * @return Claims对象
     */
    public static Claims getClaims(String token) {
        return Jwts.parser()
            .setSigningKey(SIGNING_KEY.getBytes())
            .parseClaimsJws(token)
            .getBody();
    }

    public static String getUserId() {
        String token = getToken();
        Claims claims = getClaims(token);
        return String.valueOf(claims.getId());
    }

}
