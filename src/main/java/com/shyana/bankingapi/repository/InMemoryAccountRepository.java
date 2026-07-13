package com.shyana.bankingapi.repository;

import com.shyana.bankingapi.model.Account;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

    private final Map<UUID, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public Account save(Account account) {
        accounts.put(account.getId(), account);
        return account;
    }

    @Override
    public Optional<Account> findById(UUID accountId) {
        return Optional.ofNullable(accounts.get(accountId));
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }
}