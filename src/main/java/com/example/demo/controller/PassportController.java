package com.example.demo.controller;


import com.example.demo.model.LoginResult;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/** 
* @author liushaoming(liushaomingdev@163.com)
* @version 创建时间：2017年8月26日 下午11:21:36 
* 类说明 
*/
@RequestMapping("passport")
@Controller
public class PassportController {
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("login")
	@ResponseBody
	public LoginResult login(String username, String password, Integer accountType) {
		LoginResult loginResult = loginService.login(username, password, accountType);
		return loginResult;
	}
	
	@RequestMapping("register")
	@ResponseBody
	public LoginResult register(String username, String password, Integer accountType) {
		System.out.println(username);
		LoginResult loginResult = loginService.register(username, password, accountType);
		return loginResult;
	}
}
