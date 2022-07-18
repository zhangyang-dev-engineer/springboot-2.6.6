package com.zhouyu.service;

import com.zhouyu.ZhouyuTypeExcludeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserService { // userService UserService

	@Value("${random.int}")
	private int num;

	@Autowired
	private Environment environment;

	public String test(){
		return String.valueOf(environment.getProperty("random.int,100"));
	}



}
