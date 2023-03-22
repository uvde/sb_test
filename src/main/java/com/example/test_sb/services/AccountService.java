package com.example.test_sb.services;

import com.example.test_sb.models.dto.AccountDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    AccountDto getById(Long id);
    List<AccountDto> getAllAccount();
    Long saveAccount(AccountDto accountDto);
    void updateAccount(Long id, AccountDto accountDto);
    void createTransaction(Long fromId, Long toId, BigDecimal value);
}
