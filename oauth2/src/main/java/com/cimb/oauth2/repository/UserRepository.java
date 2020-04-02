package com.cimb.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.oauth2.entity.MobileUser;


public interface UserRepository extends JpaRepository<MobileUser, Long> {
	 
    MobileUser findByUsername(String username);
}