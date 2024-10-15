package com.testbed.accountapi.service;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.testbed.accountapi.model.Account;
import com.testbed.accountapi.model.Account.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.DirtiesContext.MethodMode;

import java.time.ZonedDateTime;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
		"customer.service.name=customer-service",
		"customer.service.url=http://localhost:10001"
})
@WireMockTest(httpPort = 10001)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD, methodMode = MethodMode.BEFORE_METHOD)
public class AccountServiceTest {

	@Autowired
	private AccountService accountService;

	@BeforeEach
	public void setup() {
		stubFor(get("/customers/1").willReturn(aResponse()
				.withHeader("Content-Type", "application/json")
				.withBody("{\"id\":\"1\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"test@test.com\"}")));

		stubFor(get("/customers/2").willReturn(aResponse()
				.withStatus(404)));
	}
	
	@Test
	public void testGetAllAccounts() {
		List<Account> accounts = accountService.getAllAccounts();
		assertEquals(0, accounts.size());
	}
	
	@Test
	public void testGetAccount() {
		Account account = accountService.getAccount(1L);
		assertNull(account);
	}
	
	@Test
	public void testCreateAccount() {
		Account account = new Account();
		account.setCustomerId(1L);
		account.setName("Test Account");
		account.setNumber("12345");
		account.setOpenDate(ZonedDateTime.now());
		account.setStatus(Status.OPEN);
		
		Account createdAccount = accountService.createAccount(account);
		assertNotNull(createdAccount);
		assertNotNull(createdAccount.getId());
		assertEquals(account.getCustomerId(), createdAccount.getCustomerId());
		assertEquals(account.getName(), createdAccount.getName());
		assertEquals(account.getNumber(), createdAccount.getNumber());
		assertEquals(account.getOpenDate(), createdAccount.getOpenDate());
		assertEquals(account.getStatus(), createdAccount.getStatus());
	}

	@Test
	public void testCreateAccountWithInvalidCustomerId(){
		Account account = new Account();
		account.setCustomerId(2L);
		account.setName("Test Account");
		account.setNumber("12345");
		account.setOpenDate(ZonedDateTime.now());
		account.setStatus(Status.OPEN);

		try {
			accountService.createAccount(account);
			fail("Expected exception to be thrown");
		} catch (Exception e) {
			assertEquals("[404 Not Found] during [GET] to [http://localhost:10001/customers/2] [CustomerApiClient#getCustomer(String)]: []", e.getMessage());
		}
	}
	
	@Test
	public void testUpdateAccount() {
		Account account = new Account();
		account.setCustomerId(1L);
		account.setName("Test Account");
		account.setNumber("12345");
		account.setOpenDate(ZonedDateTime.now());
		account.setStatus(Status.OPEN);
		
		Account createdAccount = accountService.createAccount(account);
		assertNotNull(createdAccount);
		assertNotNull(createdAccount.getId());
		assertEquals(account.getCustomerId(), createdAccount.getCustomerId());
		assertEquals(account.getName(), createdAccount.getName());
		assertEquals(account.getNumber(), createdAccount.getNumber());
		assertEquals(account.getOpenDate(), createdAccount.getOpenDate());
		assertEquals(account.getStatus(), createdAccount.getStatus());
		
		createdAccount.setName("Updated Account");
		createdAccount.setNumber("54321");
		createdAccount.setCloseDate(ZonedDateTime.now());
		createdAccount.setStatus(Status.CLOSED);
		
		Account updatedAccount = accountService.updateAccount(createdAccount);
		assertNotNull(updatedAccount);
		assertNotNull(updatedAccount.getId());
		assertEquals(createdAccount.getCustomerId(), updatedAccount.getCustomerId());
		assertEquals(createdAccount.getName(), updatedAccount.getName());
		assertEquals(createdAccount.getNumber(), updatedAccount.getNumber());
		assertEquals(createdAccount.getOpenDate(), updatedAccount.getOpenDate());
		assertEquals(createdAccount.getCloseDate(), updatedAccount.getCloseDate());
		assertEquals(createdAccount.getStatus(), updatedAccount.getStatus());
	}
	
	@Test
	public void testDeleteAccount() {
		Account account = new Account();
		account.setCustomerId(1L);
		account.setName("Test Account");
		account.setNumber("12345");
		account.setOpenDate(ZonedDateTime.now());
		account.setStatus(Status.OPEN);
		
		Account createdAccount = accountService.createAccount(account);
		assertNotNull(createdAccount);
		assertNotNull(createdAccount.getId());
		assertEquals(account.getCustomerId(), createdAccount.getCustomerId());
		assertEquals(account.getName(), createdAccount.getName());
		assertEquals(account.getNumber(), createdAccount.getNumber());
		assertEquals(account.getOpenDate(), createdAccount.getOpenDate());
		assertEquals(account.getStatus(), createdAccount.getStatus());
		
		accountService.deleteAccount(createdAccount.getId());
		
		Account deletedAccount = accountService.getAccount(createdAccount.getId());
		assertNull(deletedAccount);
	}
}
