package com.zhouyu;

import com.zhouyu.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletContextInitializerBeans;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.context.support.ServletConfigPropertySource;
import org.springframework.web.context.support.StandardServletEnvironment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@ConditionalOnClass
public class MyApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(MyApplication.class);
		application.setEnvironmentPrefix("zhouyu");

		Map<String, Object> map = new HashMap<>();
		map.put("k1", "v1");
		application.setDefaultProperties(map);

		ConfigurableApplicationContext applicationContext = application.run(args);
		System.out.println(applicationContext.getEnvironment().getProperty("test"));
	}

}
