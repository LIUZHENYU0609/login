package com.example.demo.controller;

import com.example.demo.annotation.AuthController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("account")
@Controller
@AuthController
public class AccountController {
	@RequestMapping("getInfo")
	@ResponseBody
	public String getInfo() {
		System.out.println("getInfo success.");
		return "getInfo success.";
	}
}