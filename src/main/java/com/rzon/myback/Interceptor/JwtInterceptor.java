package com.rzon.myback.Interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzon.myback.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //获取前端header头携带的token
        String token = request.getHeader("Authorization");
        if(StringUtils.isEmpty(token)) {
            map.put("msg","token为空");
        }else {
            try {
                JwtUtil.verify(token);//验证令牌
                return true;//放行请求
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
        }
        map.put("code", 401);//设置状态
        map.put("data", null);
        //将map转为json jackson
        String json = new ObjectMapper().writeValueAsString(map);
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}