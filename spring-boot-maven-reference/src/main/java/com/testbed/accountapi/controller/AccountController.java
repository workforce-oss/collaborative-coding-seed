package com.testbed.accountapi.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testbed.accountapi.model.Account;
import com.testbed.accountapi.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping
	public ResponseEntity<List<Account>> getAllAccounts() {
		log.info("getAllAccounts");
		return new ResponseEntity<List<Account>>(accountService.getAllAccounts(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Account> getAccount(@PathVariable Long id) {
		log.info("getAccount: id={}", id);
		return new ResponseEntity<Account>(accountService.getAccount(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Account> createAccount(@Validated @RequestBody Account account) {
		log.info("createAccount: account={}", account);
		return new ResponseEntity<Account>(accountService.createAccount(account), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Account> updateAccount(@Validated(PutMapping.class) @RequestBody Account account) {
		log.info("updateAccount: account={}", account);
		return new ResponseEntity<Account>(accountService.updateAccount(account), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
		log.info("deleteAccount: id={}", id);
		accountService.deleteAccount(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
