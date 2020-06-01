package com.example.demo.model;

public class Passport {
    private static final long serialVersionUID = 1L;
    private Long userId=0L;
    private String token="";

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
