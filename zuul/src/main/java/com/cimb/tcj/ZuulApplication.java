package com.cimb.tcj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.cimb.tcj.filter.AccessLoggingFilter;
import com.cimb.tcj.filter.AuthorizationFilter;

@EnableResourceServer
@EnableZuulProxy
@SpringBootApplication
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}

	@Bean
	public AuthorizationFilter initAuthorizationFilter() {
		return new AuthorizationFilter();
	}

	@Bean
	public AccessLoggingFilter initAccessLoggingFilter() {
		return new AccessLoggingFilter();
	}
}
