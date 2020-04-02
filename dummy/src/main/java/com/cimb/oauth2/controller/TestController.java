package com.cimb.oauth2.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.oauth2.entity.Account;
import com.cimb.oauth2.repository.AccountRepository;

@RestController
public class TestController {

	@Autowired
	AccountRepository accountRepository;

	@GetMapping("/createAccount")
	public void createAccount() {
		Account a = new Account();
		a.setId("1");
		accountRepository.save(a);
	}

	@GetMapping("/updateAccount/{dummy}")
	public void updateAccount(@PathVariable String dummy) {
		Account a = accountRepository.getOne("1");

		a.setDummy(StringUtils.isNotBlank(dummy) ? dummy : "123");
		accountRepository.save(a);
	}

}
