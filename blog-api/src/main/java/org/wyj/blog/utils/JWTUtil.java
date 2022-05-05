package org.wyj.blog.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;

public class JWTUtil {

    private static final String secretKey = "123456orgwyj!@#$%";

    /**
     * 创建jwt的token
     * @param userId 用户ID
     * @return token
     */
    public static String createToken(Long userId) {
        HashMap<String, Object> claim = new HashMap<>();
        claim.put("userId", userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, secretKey) // 签发算法，秘钥为secretKey
                .setClaims(claim)
                .setIssuedAt(new Date())
                // 设置token的失效时间，1天后，单位是毫秒
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
        return jwtBuilder.compact();
    }

    /**
     * 验证token
     *
     * @param token 令牌
     * @return 返回令牌中的数据
     */
    public static Object checkToken(String token) {
        try {
            Jwt<?, ?> parse = Jwts.parser().setSigningKey(secretKey).parse(token);
            return parse.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
