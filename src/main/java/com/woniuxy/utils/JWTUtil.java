package com.woniuxy.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;

// JWT的工具类
public class JWTUtil {

    // 将创建jwt和解析jwt的代码抽取出来，实现代码的复用

    // 定义过期时间
    private static final Long EXPIRE_TIME = 30*60*60*1000L;
    // 随机盐获取
    private static final String SALT = SaltUtils.getSalt(8);

    // 创建jwt
    public static String createToken(HashMap<String,String> map){
        JWTCreator.Builder builder = JWT.create();
        // 循环设置key和value
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        // 设置过期时间
        builder.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME));
        // 设置jwt的加密方法:使用工具类盐获取一个随机数
        String sign = builder.sign(Algorithm.HMAC256(SALT));
        return sign;
    }

    // 解析jwt
    public static DecodedJWT verify(String token){
        // 设置jwt的解析算法
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SALT)).build();
        // 验证token的值
        DecodedJWT verify = jwtVerifier.verify(token);
        return verify;
    }

}
