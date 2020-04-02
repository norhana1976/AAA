package com.cimb.tcj.token;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

	@Override
	public Authentication extractAuthentication(Map<String, ?> map) {
		for (String key : map.keySet()) {
			System.out.println("key : " + key + "| value :" + map.get(key));
		}

		return super.extractAuthentication(map);
	}

}
