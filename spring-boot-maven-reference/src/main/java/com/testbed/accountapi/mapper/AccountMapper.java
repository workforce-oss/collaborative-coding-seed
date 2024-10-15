package com.testbed.accountapi.mapper;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.testbed.accountapi.model.Account;
import com.testbed.accountapi.persistence.AccountEntity;

@Mapper(componentModel = "spring")
public interface AccountMapper {

	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "openDate", source = "openDate", dateFormat = "yyyy-MM-dd"),
		@Mapping(target = "closeDate", source = "closeDate", dateFormat = "yyyy-MM-dd")
	})
	AccountEntity toEntity(Account account);
	
	@Mappings({
		@Mapping(target = "openDate", source = "openDate", dateFormat = "yyyy-MM-dd"),
		@Mapping(target = "closeDate", source = "closeDate", dateFormat = "yyyy-MM-dd")
	})
	Account toModel(AccountEntity accountEntity);
	
	List<Account> toModel(List<AccountEntity> accountEntities);
	
	List<AccountEntity> toEntity(List<Account> accounts);
}
