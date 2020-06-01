package com.example.demo.service;

import com.example.demo.model.Passport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginInterceptorService {
    @Autowired
    private TokenService tokenService;

    public Passport getPassport(Long userId){
        String token=tokenService.getToken(userId);
        Passport passport=new Passport();
        passport.setUserId(userId);
        passport.setToken(token);
        return passport;
    }
}
