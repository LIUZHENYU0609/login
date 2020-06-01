package com.example.demo.intercepter;

import com.example.demo.annotation.AuthController;
import com.example.demo.model.Passport;
import com.example.demo.service.LoginInterceptorService;
import com.example.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginIntercptor extends HandlerInterceptorAdapter {
    @Autowired
    private LoginInterceptorService loginInterceptorService;
    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)){
            HandlerMethod handlerMethod=(HandlerMethod) handler;
            Object controller=handlerMethod.getBean();
            boolean present=controller.getClass().isAnnotationPresent(AuthController.class);
            if(!present){
                return  true;
            }
            String userIdStr=request.getHeader("userId");
            String token=request.getHeader("token");
            if (!StringUtils.isEmpty(userIdStr)){
                if (StringUtils.isEmpty(token)){
                    return false;
                }
                Passport passport = loginInterceptorService.getPassport(Long.valueOf(userIdStr));
                String storedToken = passport.getToken();
                if (StringUtils.isEmpty(storedToken)){
                    return false;
                }
                else if (!storedToken.equals(token)){
                    return false;
                }
            }
            else {
                return false;
            }
        }
        return true;
    }


}
