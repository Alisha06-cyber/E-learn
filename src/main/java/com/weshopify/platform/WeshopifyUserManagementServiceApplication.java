package com.weshopify.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class WeshopifyUserManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeshopifyUserManagementServiceApplication.class, args);
	}

	/* when application is start the bean is aanotatied menas object of rest
	// tempalte create
	// THIS BEAN NOT DELECRE PUBLIC becuse the scope of the bean is singlton menas
	// thorghut the
	only one type resttemplate create*/
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
