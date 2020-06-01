package com.example.demo.service;

import com.example.demo.redis.CommonCacheKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class TokenRedisService {
    @Autowired
    private JedisPool jedisPool;

    public String getToken(Long userId){
        Jedis jedis=jedisPool.getResource();
        String key= CommonCacheKey.PASSPORT_PREFIX+userId;
        String tokenInRedis=jedis.get(key);
        jedis.close();
        return tokenInRedis;
    }

    public String setToken(Long userId,String token)
    {
        Jedis jedis=jedisPool.getResource();
        String key=CommonCacheKey.PASSPORT_PREFIX+userId;
        String result=jedis.set(key,token);
        jedis.close();
        return result;
    }
}
