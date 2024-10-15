package com.testbed.accountapi.service;
import java.util.List;
import java.util.Optional;

import com.testbed.accountapi.integration.customerapi.Customer;
import com.testbed.accountapi.integration.customerapi.CustomerApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.testbed.accountapi.mapper.AccountMapper;
import com.testbed.accountapi.model.Account;
import com.testbed.accountapi.persistence.AccountEntity;
import com.testbed.accountapi.repository.AccountRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountService {

	private AccountRepository accountRepository;
	private AccountMapper accountMapper;

	private CustomerApiClient customerApiClient;
	
	public List<Account> getAllAccounts() {
		return accountMapper.toModel(accountRepository.findAll());
	}
	
	public Account getAccount(Long id) {
		Optional<AccountEntity> accountEntity = accountRepository.findById(id);
		return accountEntity.isPresent() ? accountMapper.toModel(accountEntity.get()) : null;
	}
	
	public Account createAccount(Account account) {
		Customer customer = customerApiClient.getCustomer(account.getCustomerId().toString());
		if (customer == null) {
			throw new RuntimeException("Customer not found");
		}
		AccountEntity accountEntity = accountMapper.toEntity(account);
		accountEntity = accountRepository.save(accountEntity);
		return accountMapper.toModel(accountEntity);
	}
	
	public Account updateAccount(Account account) {
		AccountEntity accountEntity = accountMapper.toEntity(account);
		Optional<AccountEntity> accountEntityInDb = accountRepository.findById(account.getId());
		if (accountEntityInDb.isPresent()) {
			accountEntity.setId(accountEntityInDb.get().getId());
			accountEntity = accountRepository.save(accountEntity);
			return accountMapper.toModel(accountEntity);
		} else {
			return null;
		}
	}
	public void deleteAccount(Long id) {
		accountRepository.deleteById(id);
	}
}
