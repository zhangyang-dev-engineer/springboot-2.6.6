package com.zhouyu;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class ZhouyuApplicationContextInitializer implements ApplicationContextInitializer {
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		applicationContext.getBeanFactory().registerSingleton("zhouyuTypeExcludeFilter", new ZhouyuTypeExcludeFilter());
	}
}
