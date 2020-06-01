package com.example.demo.service;

import com.example.demo.DAO.TokenDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
public class TokenService {

    @Autowired
    private TokenDAO tokenDAO;
    @Autowired
    private TokenRedisService tokenRedisService;

    public String getToken(Long userId){
        String cacheToken=tokenRedisService.getToken(userId);
        String token="";
        if(StringUtils.isEmpty(cacheToken)){
            token=tokenDAO.getToken(userId);
        }
        else {
            token=cacheToken;
        }
        return token;
    }

    public String copyTokenToCache(Long userId) {
        String tokenInDB = tokenDAO.getToken(userId);
        tokenRedisService.setToken(userId, tokenInDB);
        return tokenInDB;
    }

    public Integer insert(Long userId, String token, Long expire) {
        Integer count = tokenDAO.insert(userId, token, expire);
        if (count > 0) {
            tokenRedisService.setToken(userId, token);
        }
        return count;
    }

    public Integer updateToken(Long userId, String token) {
        Integer count = tokenDAO.updateToken(userId, token);
        if (count > 0) {
            tokenRedisService.setToken(userId, token);
        }
        return count;
    }
}
