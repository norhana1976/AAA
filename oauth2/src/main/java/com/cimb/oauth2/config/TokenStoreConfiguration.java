package com.cimb.oauth2.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
public class TokenStoreConfiguration {

	@Bean
	public TokenStore tokenStore(DataSource dataSource) {
		return new JdbcTokenStore(dataSource);
	}
}
