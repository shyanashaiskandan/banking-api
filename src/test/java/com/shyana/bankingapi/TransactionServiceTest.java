package com.shyana.bankingapi;

import com.shyana.bankingapi.dto.AccountResponse;
import com.shyana.bankingapi.dto.CreateAccountRequest;
import com.shyana.bankingapi.dto.TransactionRequest;
import com.shyana.bankingapi.dto.TransactionResponse;
import com.shyana.bankingapi.repository.AccountRepository;
import com.shyana.bankingapi.repository.InMemoryAccountRepository;
import com.shyana.bankingapi.repository.InMemoryTransactionRepository;
import com.shyana.bankingapi.repository.TransactionRepository;
import com.shyana.bankingapi.service.AccountService;
import com.shyana.bankingapi.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionServiceTest {

    private AccountService accountService;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        AccountRepository accountRepository =
                new InMemoryAccountRepository();

        TransactionRepository transactionRepository =
                new InMemoryTransactionRepository();

        accountService = new AccountService(accountRepository);

        transactionService = new TransactionService(
                accountRepository,
                transactionRepository
        );
    }

    // Checks that money is removed from one account and added to another.
    @Test
    void transfersMoneyBetweenAccounts() {
        AccountResponse sourceAccount =
                accountService.createAccount(
                        new CreateAccountRequest(
                                "Source",
                                new BigDecimal("1000.00")
                        )
                );

        AccountResponse destinationAccount =
                accountService.createAccount(
                        new CreateAccountRequest(
                                "Destination",
                                new BigDecimal("200.00")
                        )
                );

        TransactionRequest request =
                new TransactionRequest(
                        sourceAccount.id(),
                        destinationAccount.id(),
                        new BigDecimal("100.00")
                );

        transactionService.createTransaction(request);

        AccountResponse updatedSource =
                accountService.getAccount(sourceAccount.id());

        AccountResponse updatedDestination =
                accountService.getAccount(destinationAccount.id());

        assertEquals(
                new BigDecimal("900.00"),
                updatedSource.balance()
        );

        assertEquals(
                new BigDecimal("300.00"),
                updatedDestination.balance()
        );
    }

    // Checks that a transfer fails when the source account has insufficient funds.
    @Test
    void preventsTransferWithInsufficientFunds() {
        AccountResponse sourceAccount =
                accountService.createAccount(
                        new CreateAccountRequest(
                                "Source",
                                new BigDecimal("50.00")
                        )
                );

        AccountResponse destinationAccount =
                accountService.createAccount(
                        new CreateAccountRequest(
                                "Destination",
                                BigDecimal.ZERO
                        )
                );

        TransactionRequest request =
                new TransactionRequest(
                        sourceAccount.id(),
                        destinationAccount.id(),
                        new BigDecimal("100.00")
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.createTransaction(request)
        );
    }

    // Checks that an account cannot transfer money to itself.
    @Test
    void preventsTransferToSameAccount() {
        AccountResponse account =
                accountService.createAccount(
                        new CreateAccountRequest(
                                "Shyana",
                                new BigDecimal("500.00")
                        )
                );

        TransactionRequest request =
                new TransactionRequest(
                        account.id(),
                        account.id(),
                        new BigDecimal("100.00")
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.createTransaction(request)
        );
    }

    // Checks that a completed transaction appears in the account history.
    @Test
    void returnsTransactionHistory() {
        AccountResponse sourceAccount =
                accountService.createAccount(
                        new CreateAccountRequest(
                                "Source",
                                new BigDecimal("500.00")
                        )
                );

        AccountResponse destinationAccount =
                accountService.createAccount(
                        new CreateAccountRequest(
                                "Destination",
                                BigDecimal.ZERO
                        )
                );

        transactionService.createTransaction(
                new TransactionRequest(
                        sourceAccount.id(),
                        destinationAccount.id(),
                        new BigDecimal("100.00")
                )
        );

        List<TransactionResponse> transactions =
                transactionService.getTransactions(sourceAccount.id());

        assertEquals(1, transactions.size());
        assertEquals(
                new BigDecimal("100.00"),
                transactions.get(0).amount()
        );
    }
}