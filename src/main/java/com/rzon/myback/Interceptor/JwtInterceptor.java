package com.rzon.myback.Interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzon.myback.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 */
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //获取前端header头携带的token
        String token = request.getHeader("Authorization");
        if(StringUtils.isEmpty(token)) {
            map.put("msg","token为空");
            map.put("code", 401);//设置状态
            response.setStatus(401);
        }else {
            try {
                JwtUtil.verify(token);//验证令牌
                String userId = JwtUtil.getPayload(token, "userId"); //获取payload中的用户id
                String hasToken = stringRedisTemplate.opsForValue().get(userId); //根据userId获取redis中存储的令牌
                if(hasToken != null && !hasToken.isEmpty()) {
                    //校验客户端携带的令牌与redis中的令牌是否一致
                    String uuidRedis = JwtUtil.getPayload(hasToken, "uuid");
                    String uuidClient = JwtUtil.getPayload(token, "uuid");
                    if(uuidRedis.equals(uuidClient)) {
                        return true;
                    }else {
                        map.put("msg","账号已在其他设备登录");
                    }
                }else {
                    map.put("msg","账号已在其他设备登录");
                }
            } catch (SignatureVerificationException e) {
                e.printStackTrace();
                map.put("msg","拦截器拦截:无效签名");
            }catch (TokenExpiredException e) {
                e.printStackTrace();
                map.put("msg","拦截器拦截:token过期");
            }catch (AlgorithmMismatchException e) {
                e.printStackTrace();
                map.put("msg","拦截器拦截:token算法不一致");
            }catch (Exception e) {
                e.printStackTrace();
                map.put("msg","拦截器拦截:token无效");
            }
            map.put("code", 401);//设置状态
            response.setStatus(401);
        }
        map.put("data", null);
        //将map转为json jackson
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}