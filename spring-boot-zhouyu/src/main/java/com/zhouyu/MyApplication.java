package com.zhouyu;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MyApplication {

	@Bean
	public TomcatConnectorCustomizer tomcatConnectorCustomizer(){
		return new TomcatConnectorCustomizer() {
			@Override
			public void customize(Connector connector) {
				connector.setPort(8888);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class);
	}

}
