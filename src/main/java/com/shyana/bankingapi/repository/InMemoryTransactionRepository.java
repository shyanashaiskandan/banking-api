package com.shyana.bankingapi.repository;

import com.shyana.bankingapi.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public Transaction save(Transaction transaction) {
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findByAccountId(UUID accountId) {
        List<Transaction> accountTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getSourceAccountId().equals(accountId)
                    || transaction.getDestinationAccountId().equals(accountId)) {
                accountTransactions.add(transaction);
            }
        }

        return accountTransactions;
    }
}