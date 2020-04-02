package com.cimb.oauth2.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import com.cimb.oauth2.service.UserService;

@Component
public class NexmoAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private UserService userService;
	
	@Value("${security.oauth2.client.client-id}")
	private String clientID;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		userService.loadUserByUsername(name);
		//TODO: link nexmo auth call heres
		if (passwordEncoder.matches(password, passwordEncoder.encode("password"))) {
			// if is valid, remove all token
			 resetAllToken(name);
			return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
		} else {
			throw new BadCredentialsException("invalid user/password");
		}
	}

	private void resetAllToken(@NotBlank String name) {
		Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(clientID, name);
		for (OAuth2AccessToken oAuth2AccessToken : tokens) {
			tokenStore.removeAccessToken(oAuth2AccessToken);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}