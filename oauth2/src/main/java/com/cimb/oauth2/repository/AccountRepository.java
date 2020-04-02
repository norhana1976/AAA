package com.cimb.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.oauth2.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String>{

}
