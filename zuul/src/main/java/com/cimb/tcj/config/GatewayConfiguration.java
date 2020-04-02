package com.cimb.tcj.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class GatewayConfiguration extends ResourceServerConfigurerAdapter {

	@Value("${security.oauth2.resource.opaque-token.client-secret}")
	private String clientSecret;
	@Value("${security.oauth2.resource.opaque-token.client-id}")
	private String clientId;
	@Value("${security.oauth2.resource.opaque-token.introspection-uri}")
	private String tokenCheckEndPoint;

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests()
			.antMatchers("/auth/**").permitAll()
			.anyRequest().authenticated();
		// @formatter:on
	}

	@Bean
	public ResourceServerTokenServices initResourceServerTokenServices() {

		RemoteTokenServices tokenServices = new RemoteTokenServices();
		tokenServices.setCheckTokenEndpointUrl(tokenCheckEndPoint);
		tokenServices.setClientId(clientId);
		tokenServices.setClientSecret(clientSecret);
		// TODO: create a custom access token mapper for AUTH
		// tokenServices.setAccessTokenConverter(converter)
		return tokenServices;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("cl-app");
	}

}