package com.shyana.bankingapi;

import com.shyana.bankingapi.dto.AccountResponse;
import com.shyana.bankingapi.dto.CreateAccountRequest;
import com.shyana.bankingapi.repository.AccountRepository;
import com.shyana.bankingapi.repository.InMemoryAccountRepository;
import com.shyana.bankingapi.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountServiceTest {

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        AccountRepository accountRepository =
                new InMemoryAccountRepository();

        accountService = new AccountService(accountRepository);
    }

    // Checks that a new account is created with the correct information.
    @Test
    void createsAccount() {
        CreateAccountRequest request =
                new CreateAccountRequest(
                        "Shyana",
                        new BigDecimal("1000.00")
                );

        AccountResponse account =
                accountService.createAccount(request);

        assertNotNull(account.id());
        assertEquals("Shyana", account.ownerName());
        assertEquals(
                new BigDecimal("1000.00"),
                account.balance()
        );
        assertNotNull(account.createdAt());
    }

    // Checks that an existing account can be found using its ID.
    @Test
    void getsAccount() {
        AccountResponse createdAccount =
                accountService.createAccount(
                        new CreateAccountRequest(
                                "Shyana",
                                new BigDecimal("500.00")
                        )
                );

        AccountResponse foundAccount =
                accountService.getAccount(createdAccount.id());

        assertEquals(createdAccount.id(), foundAccount.id());
        assertEquals("Shyana", foundAccount.ownerName());
    }
}