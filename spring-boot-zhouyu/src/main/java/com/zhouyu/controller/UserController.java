package com.zhouyu.controller;

import com.zhouyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

//@RestController
public class UserController {

	@Autowired
	private UserService userService;

//	@GetMapping("/")
	public String test(){
		return userService.test();
	}
}
