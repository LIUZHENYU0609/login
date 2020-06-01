package com.example.demo.service;

import com.example.demo.DAO.UserDAO;
import com.example.demo.constant.AccountType;
import com.example.demo.constant.AuthResponseCode;
import com.example.demo.constant.UserStatus;
import com.example.demo.model.LoginResult;
import com.example.demo.model.User;
import com.example.demo.util.DateTool;
import com.example.demo.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginService {
    private static final Long tokenExpire=1000000000L;

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TokenService tokenService;
    //判断用户是否存在->设置用户名->产生token->插入token到数据库并加载到redis->插入用户
    public LoginResult register(String username,String password,Integer accountType){
        User user=new User();
        LoginResult loginResult=new LoginResult();
        boolean exis=existUsername(username,accountType);
        boolean registerSuccess=false;
        if(exis){
            System.out.println("This user exists! " + username);
            loginResult.setUserId(0L);
            loginResult.setUsername(username);
            loginResult.setToken("");
            loginResult.setCode(AuthResponseCode.USER_REPEAT);
            loginResult.setCodeDesc(AuthResponseCode.USER_REPEAT_DESC);
        }
        else{
            String encodedPassword = MD5Util.md5(password).toLowerCase();
            user.setPassword(encodedPassword);
            boolean setSuccess = setUsername(username, accountType, user);
            int insertedCount = 0;
            if (setSuccess){
                user.setStatus(UserStatus.active);
                insertedCount=userDAO.insert(user);
                Long userId=user.getId();
                if(insertedCount==1){
                    // 产生token并存入数据库
                    String token = MD5Util.md5(DateTool.getTime() + password).toLowerCase();
                    Integer count = tokenService.insert(userId, token, tokenExpire);
                    if (count > 0) {
                        loginResult.setUserId(userId);
                        loginResult.setToken(token);
                        loginResult.setUsername(username);
                        loginResult.setCode(AuthResponseCode.SUCCESS);
                        loginResult.setCodeDesc(AuthResponseCode.SUCCESS_DESC);
                        registerSuccess = true;
                    }
                }
            }
            if (!registerSuccess) {
                loginResult.setUserId(0L);
                loginResult.setToken("");
                loginResult.setUsername(username);
                loginResult.setCode(AuthResponseCode.USER_REGISTER_FAILED);
                loginResult.setCodeDesc(AuthResponseCode.USER_REGISTER_FAILED_DESC);
            }
        }
        return loginResult;
    }
    //判断用户密码是否正确，正确则将用户的token加载到redis中；否则返回登录失败
    public LoginResult login(String username,String password,Integer accountType){
        String encodedPassword = MD5Util.md5(password).toLowerCase();
        User user = new User();
        switch (accountType) {
            case AccountType.MOBILE:
                if (username.length() == 11 && username.charAt(0) == '1') {
                    user = userDAO.getUserByMobile(username);
                }
                break;
            case AccountType.EMAIL:
                if (username.contains("@")) {
                    user = userDAO.getUserByEmail(username);
                }
                break;
        }
        String passwordInDB =user.getPassword();
        Long userId = user.getId();
        String code = AuthResponseCode.USERCENTER_ERROR;
        String desc = AuthResponseCode.USERCENTER_ERROR_DESC;
        String token = "";
        if (encodedPassword.equals(passwordInDB)) {
            token = tokenService.copyTokenToCache(userId);
            code = AuthResponseCode.SUCCESS;
            desc = AuthResponseCode.SUCCESS_DESC;
        }
        LoginResult loginResult = new LoginResult();
        loginResult.setUserId(userId);
        loginResult.setToken(token);
        loginResult.setUsername(username);
        loginResult.setCode(code);
        loginResult.setCodeDesc(desc);
        return loginResult;
    }
    private boolean existUsername(String username,Integer accountType)
    {
        Integer count=0;
        switch (accountType){
            case AccountType.MOBILE:
                if(username.length()==11&&username.charAt(0)=='1'){
                    userDAO.exist("mobile",username);
                }
                break;
            case AccountType.EMAIL:
                if (username.contains("@")){
                    count=userDAO.exist("email",username);
                }
                break;
        }
        return count>0;
    }
    private boolean setUsername(String username,Integer accountType,User user){
        boolean success=false;
        switch (accountType){
            case AccountType.MOBILE:
                if (username.length() == 11 && username.charAt(0) == '1') {
                    user.setMobile(username);
                    success = true;
                }
                break;
            case AccountType.EMAIL:
                if (username.contains("@")){
                    user.setEmail(username);
                    success = true;
                }
                break;
            case AccountType.WECHAT:
                user.setOpenId(username);
                success = true;
                break;
        }
        return success;
    }
}
