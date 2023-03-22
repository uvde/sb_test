package com.example.test_sb.mappers.impl;

import com.example.test_sb.mappers.AccountMapper;
import com.example.test_sb.models.dto.AccountDto;
import com.example.test_sb.models.entities.Account;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account accountDtoToAccount(AccountDto accountDto) {
        return new Account(accountDto.getValue(), accountDto.getName());
    }

    @Override
    public List<AccountDto> accountListEntityToListAccountDto(List<Account> accounts) {
        return accounts.stream().map(this::accountToAccountDto).collect(Collectors.toList());
    }

    @Override
    public AccountDto accountToAccountDto(Account account) {
        return new AccountDto(account.getValue(), account.getName());
    }
}
