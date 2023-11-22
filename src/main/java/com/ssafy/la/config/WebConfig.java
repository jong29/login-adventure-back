package com.ssafy.la.config;

import com.ssafy.la.user.model.dao.UserRedisDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.la.interceptor.GlobalInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:5173")
				.allowedMethods("GET", "POST", "OPTIONS")
				.allowedHeaders("*")
				.maxAge(3600);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(globalInterceptor())
				.addPathPatterns("/user/**");
	}

	@Bean
	@DependsOn("userRedisDao")
	public GlobalInterceptor globalInterceptor() {
		return new GlobalInterceptor();
	}

	@Bean
	public UserRedisDao userRedisDao() {
		return new UserRedisDao();
	}
}
