package com.shyana.bankingapi.repository;

import com.shyana.bankingapi.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {

    Transaction save(Transaction transaction);

    List<Transaction> findByAccountId(UUID accountId);
}