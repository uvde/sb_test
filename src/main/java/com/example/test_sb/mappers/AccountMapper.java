package com.example.test_sb.mappers;

import com.example.test_sb.models.dto.AccountDto;
import com.example.test_sb.models.entities.Account;

import java.util.List;

public interface AccountMapper {
    List<AccountDto> accountListEntityToListAccountDto(List<Account> accounts);
    AccountDto accountToAccountDto(Account account);
    Account accountDtoToAccount(AccountDto accountDto);
}
