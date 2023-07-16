package com.dinger.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Value("${app.jwt.token.name}")
	private String tokenName;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOriginPatterns("*")
			.allowedHeaders("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			.exposedHeaders(tokenName);
	}
}
