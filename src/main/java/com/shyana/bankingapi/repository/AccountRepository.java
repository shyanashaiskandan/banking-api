package com.shyana.bankingapi.repository;

import com.shyana.bankingapi.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

    Account save(Account account);

    Optional<Account> findById(UUID accountId);

    List<Account> findAll();
}