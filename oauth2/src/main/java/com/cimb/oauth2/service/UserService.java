package com.cimb.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cimb.oauth2.entity.MobileUser;
import com.cimb.oauth2.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		MobileUser user = userRepository.findByUsername(username);
		if (user == null) {
			user = userRepository.save(createUser(username));
		}
		else {
			user.setDummy("1234444");
			userRepository.save(user);	
		}
		
		return user;
	}
	
	private MobileUser createUser(String username) {
		MobileUser user = new MobileUser();
		user.setUsername(username);
		return user;
	}
}