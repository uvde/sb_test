package com.example.test_sb.controllers;

import com.example.test_sb.models.dto.AccountDto;
import com.example.test_sb.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("v1/accounts")
public class AccountsController {

    private final AccountService accountService;

    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public List<AccountDto> getAllAccount(){
        return accountService.getAllAccount();
    }

    @GetMapping("/{fromId}/{toId}/{value}")
    public void createTransaction(@PathVariable("fromId") Long fromId,
                                  @PathVariable("toId") Long toId,
                                  @PathVariable("value") BigDecimal value){
        accountService.createTransaction(fromId, toId, value);
    }

    @PostMapping("/")
    public Long saveAccount(@RequestBody AccountDto accountDto){
        return accountService.saveAccount(accountDto);
    }
}
