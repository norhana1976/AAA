package com.cimb.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfiguration{
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		// TODO: implement proper password encoder
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
