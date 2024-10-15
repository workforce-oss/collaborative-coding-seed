package com.testbed.accountapi.controller;

import com.testbed.accountapi.model.Account;
import com.testbed.accountapi.model.Account.Status;
import com.testbed.accountapi.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

	@Mock
	private AccountService accountService;
	
	@InjectMocks
	private AccountController accountController;
	
	private List<Account> accounts;
	
	@BeforeEach
	public void setup() {
		accounts = new ArrayList<>();
		accounts.add(new Account(1L, 1L, "Test Account 1", "123456789", ZonedDateTime.now(), null, Status.OPEN));
		accounts.add(new Account(2L, 1L, "Test Account 2", "987654321", ZonedDateTime.now(), null, Status.OPEN));
	}
	
	@Test
	public void testGetAllAccounts() {
		when(accountService.getAllAccounts()).thenReturn(accounts);
		ResponseEntity<List<Account>> response = accountController.getAllAccounts();
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
	}
	
	@Test
	public void testGetAccount() {
		when(accountService.getAccount(1L)).thenReturn(accounts.get(0));
		ResponseEntity<Account> response = accountController.getAccount(1L);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1L, response.getBody().getId());
	}
	
	@Test
	public void testCreateAccount() {
		Account account = new Account(3L, 2L, "Test Account 3", "123456789", ZonedDateTime.now(), null, Status.OPEN);
		when(accountService.createAccount(account)).thenReturn(account);
		ResponseEntity<Account> response = accountController.createAccount(account);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(3L, response.getBody().getId());
	}
	
	@Test
	public void testUpdateAccount() {
		Account account = new Account(1L,3L, "Test Account 1", "123456789", ZonedDateTime.now(), null, Status.OPEN);
		when(accountService.updateAccount(account)).thenReturn(account);
		ResponseEntity<Account> response = accountController.updateAccount(account);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1L, response.getBody().getId());
	}
	
	@Test
	public void testDeleteAccount() {
		ResponseEntity<Void> response = accountController.deleteAccount(1L);
		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
}
