package com.DrinkApp.Fun;

import com.DrinkApp.Fun.Config.RateLimitingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FunApplication {

	public static void main(String[] args) {

		SpringApplication.run(FunApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilter() {

		FilterRegistrationBean<RateLimitingFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new RateLimitingFilter());
		registration.addUrlPatterns("/api/*");
		registration.setOrder(1);
		return registration;

	}
}
