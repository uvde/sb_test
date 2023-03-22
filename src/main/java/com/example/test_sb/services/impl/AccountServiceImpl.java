package com.example.test_sb.services.impl;

import com.example.test_sb.mappers.AccountMapper;
import com.example.test_sb.models.dto.AccountDto;
import com.example.test_sb.models.entities.Account;
import com.example.test_sb.repositories.AccountRepository;
import com.example.test_sb.services.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountDto getById(Long id) {
        return null;
    }

    @Override
    public List<AccountDto> getAllAccount() {
        return accountMapper.accountListEntityToListAccountDto(
                (List<Account>) accountRepository.findAll());
    }

    @Override
    public Long saveAccount(AccountDto accountDto) {
        Account account = accountMapper.accountDtoToAccount(accountDto);
        accountRepository.save(account);
        return account.getId();
    }

    @Override
    public void updateAccount(Long id, AccountDto accountDto) {

    }

    @Override
    public void createTransaction(Long fromId, Long toId, BigDecimal value) {
        Account accountFrom = new Account();
        Account accountTo = new Account();
        Optional<Account> optionalAccountFrom = accountRepository.findById(fromId);
        Optional<Account> optionalAccountTo = accountRepository.findById(toId);

        if(optionalAccountFrom.isPresent()){
            accountFrom = optionalAccountFrom.get();
        }else {
            throw new NullPointerException("invalid sender");
        }

        if(optionalAccountTo.isPresent()){
            accountTo = optionalAccountTo.get();
        }else {
            throw new NullPointerException("invalid recipient");
        }

        if (accountFrom.getValue().compareTo(value) < 0){
            throw new IllegalArgumentException("there isn`t any money" + value);
        } else {
            accountFrom.setValue(accountFrom.getValue().subtract(value));
            accountTo.setValue(accountTo.getValue().add(value));
        }
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
    }
}
