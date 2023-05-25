package com.rzon.myback.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rzon.myback.entity.User;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Calendar;

/**
 * @author admin
 */
public class JwtUtil {

    private static final String JWTKEY = "7786df7fc3a34e26a61c034d5ec8245d";

    /**
     * 由字符串生成加密key
     * @return
     */
    public static SecretKey generalKey(){
        String stringKey = JWTKEY;//本地配置文件中加密的密文7786df7fc3a34e26a61c034d5ec8245d Constant.JWT_SECRET
        byte[] encodedKey = Base64.decodeBase64(stringKey);//本地的密码解码[B@152f6e2
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");// 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。（后面的文章中马上回推出讲解Java加密和解密的一些算法）
        return key;
    }

    /**
     * 获取token
     * @param u user
     * @return token
     */
    public static String getToken(User u) {
        Calendar instance = Calendar.getInstance();
        //默认令牌过期时间7天
        instance.add(Calendar.DATE, 7);

        JWTCreator.Builder builder = JWT.create();

        builder.withClaim("userId", u.getId())
                .withClaim("username", u.getTickname());

        SecretKey key = generalKey();
        return builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(key.getEncoded()));
    }

    /**
     * 验证token合法性 成功返回token
     */
    public static String verify(String token) throws Exception {
        SecretKey key = generalKey();
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(key.getEncoded())).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        System.out.println(decodedJWT.getClaim("userId").asString());
        return decodedJWT.getClaim("userId").asString();
    }
}