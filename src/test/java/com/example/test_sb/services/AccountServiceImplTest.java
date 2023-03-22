package com.example.test_sb.services;

import com.example.test_sb.mappers.impl.AccountMapperImpl;
import com.example.test_sb.models.dto.AccountDto;
import com.example.test_sb.models.entities.Account;
import com.example.test_sb.repositories.AccountRepository;
import com.example.test_sb.services.impl.AccountServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Spy
    private AccountMapperImpl accountMapper;

    public List<Account> accounts;

    @BeforeEach
    public void addList() {
        log.info("BeforeEach");
        //arrange
        accounts = new ArrayList<>();
        accounts.add(new Account(new BigDecimal(1000), "vasia"));
        accounts.add(new Account(new BigDecimal(5000), "sofiy"));
        accounts.add(new Account(new BigDecimal(3000), "vlad"));
        accounts.add(new Account(new BigDecimal(3000), "alexei"));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(accounts.get(0)));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(accounts.get(1)));
        when(accountRepository.findById(4L)).thenReturn(Optional.ofNullable(null));
        when(accountRepository.findAll()).thenReturn(accounts);

    }

    @Test
    public void correctGetTest() {

        log.info("correctGetTest");
        //act
        List<AccountDto> accountDtoList = accountService.getAllAccount();
        //assert
        assertEquals(4, accountDtoList.size());
    }

    @ParameterizedTest
    @MethodSource(value = "provideIdAndValueForCorrectTest")
    public void correctTransactionTest(Long fromId, Long toId, BigDecimal money, Integer expectedValueSander, Integer expectedValueRecipient) {
        log.info("correctTransaction");
        //act
        accountService.createTransaction(fromId, toId, money);
        //assert
        assertEquals(new BigDecimal(expectedValueSander), accounts.get((int) (fromId - 1L)).getValue());
        assertEquals(new BigDecimal(expectedValueRecipient), accounts.get((int) (toId - 1L)).getValue());
        verify(accountRepository, times(2)).save(any(Account.class));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1001, 2000, 1000.08})
    public void incorrectValueTest(double value) {
        log.info("incorrectValue");
        //arrange
        BigDecimal money = new BigDecimal(value);
        //act
        IllegalArgumentException trows = assertThrows(IllegalArgumentException.class, () -> {
            accountService.createTransaction(1l, 2l, money);
        });
        //assert
        assertEquals("there isn`t any money" + money, trows.getMessage());
        assertEquals(new BigDecimal(1000), accounts.get(0).getValue());
        assertEquals(new BigDecimal(5000), accounts.get(1).getValue());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    public void incorrectAccountTest() {
        //arrange
        BigDecimal value = new BigDecimal(1001);
        //act
        NullPointerException trows = assertThrows(NullPointerException.class, () -> {
            accountService.createTransaction(4l, 2l, value);
        });
        //assert
        assertEquals("invalid sender", trows.getMessage());
    }

    @Test
    public void saveAccountTest() {

        when(accountRepository.save(any(Account.class))).then(new SaveAccountAnswer());

        accountService.saveAccount(new AccountDto(new BigDecimal(5290), "kristina"));

        assertEquals(5, accounts.size());
    }

    @AfterEach
    public void teardown() {
        log.info("clear");
        accounts.clear();
    }

    class SaveAccountAnswer implements Answer<Account> {
        @Override
        public Account answer(InvocationOnMock invocationOnMock) throws Throwable {
            Account account = invocationOnMock.getArgument(0);
            accounts.add(account);
            return account;
        }
    }

    private static Stream<Arguments> provideIdAndValueForCorrectTest() {
        return Stream.of(
                Arguments.of(1L, 2L, new BigDecimal(200), 800, 5200),
                Arguments.of(2L, 1L, new BigDecimal(3300), 1700, 4300)
        );
    }
}
