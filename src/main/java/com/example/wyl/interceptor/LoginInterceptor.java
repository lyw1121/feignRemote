package com.example.wyl.interceptor;

import com.alibaba.fastjson.JSON;
import com.djcps.redis.client.RedisClient;
import com.example.wyl.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {


    @Autowired
    RedisClient redisClient;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        Cookie[] cookies = httpServletRequest.getCookies();
        //cookies数组遍历出来 与redis中的值比较
        if (cookies != null) {
            for (Cookie coo : cookies) {
                if ("token".equals(coo.getName())) {
                    String s = redisClient.get(coo.getValue());
                    User user = JSON.parseObject(s, User.class);
                    if (user!=null) {
                        return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public void postHandle (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object
        o, ModelAndView modelAndView) throws Exception {

        }

        @Override
        public void afterCompletion (HttpServletRequest httpServletRequest, HttpServletResponse
        httpServletResponse, Object o, Exception e) throws Exception {

        }
    }
