package com.shyana.bankingapi.service;

import com.shyana.bankingapi.dto.AccountResponse;
import com.shyana.bankingapi.dto.CreateAccountRequest;
import com.shyana.bankingapi.model.Account;
import com.shyana.bankingapi.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponse createAccount(CreateAccountRequest request) {
        Account account = new Account(
                UUID.randomUUID(),
                request.ownerName(),
                request.initialBalance(),
                Instant.now()
        );

        accountRepository.save(account);

        return new AccountResponse(
                account.getId(),
                account.getOwnerName(),
                account.getBalance(),
                account.getCreatedAt()
        );
    }
}