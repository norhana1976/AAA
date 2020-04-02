package com.cimb.oauth2.config;

import java.security.KeyPair;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.cimb.oauth2.service.UserService;

@Configuration
public class AuthorizationServerSecurityConfiguration extends AuthorizationServerConfigurerAdapter {
	@Value("${security.oauth2.client.client-id}")
	private String clientID;
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenStore tokenStore;

	private AuthenticationManager authenticationManager;
	private KeyPair keyPair;
	private boolean jwtEnabled;
	private UserService userService;

	public AuthorizationServerSecurityConfiguration(AuthenticationConfiguration authenticationConfiguration, KeyPair keyPair,
			@Value("${security.oauth2.authorizationserver.jwt.enabled:true}") boolean jwtEnabled,
			UserService userService) throws Exception {
		this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
		this.keyPair = keyPair;
		this.jwtEnabled = jwtEnabled;
		this.userService = userService;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

		endpoints.userDetailsService(userService)
				 .authenticationManager(this.authenticationManager)
				 .tokenStore(tokenStore);

		if (this.jwtEnabled) {
			endpoints.accessTokenConverter(accessTokenConverter());
		}
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
		        .checkTokenAccess("isAuthenticated()")
				.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory().withClient(clientID)
						  .authorizedGrantTypes("password", "refresh_token")
				          .secret(passwordEncoder.encode(clientSecret))
				          .scopes("read")
				          .accessTokenValiditySeconds(6000)
				          .refreshTokenValiditySeconds(6000);

	}
	

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setKeyPair(this.keyPair);

		DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
		accessTokenConverter.setUserTokenConverter(new SubjectAttributeUserTokenConverter());
		converter.setAccessTokenConverter(accessTokenConverter);
		converter.setSigningKey("kfc-nugget-RM20-for-20!");
		return converter;
	}
	
}

//TODO: write a proper token converter
class SubjectAttributeUserTokenConverter extends DefaultUserAuthenticationConverter {
	@Override
	public Map<String, ?> convertUserAuthentication(Authentication authentication) {
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("sub", authentication.getName());
		if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
			response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
		}
		return response;
	}
}