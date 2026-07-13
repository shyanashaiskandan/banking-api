package com.shyana.bankingapi.service;

import com.shyana.bankingapi.dto.TransactionRequest;
import com.shyana.bankingapi.dto.TransactionResponse;
import com.shyana.bankingapi.model.Account;
import com.shyana.bankingapi.model.Transaction;
import com.shyana.bankingapi.repository.AccountRepository;
import com.shyana.bankingapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository
    ) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public TransactionResponse createTransaction(TransactionRequest request) {
        Account sourceAccount = accountRepository
                .findById(request.sourceAccountId())
                .orElseThrow();

        Account destinationAccount = accountRepository
                .findById(request.destinationAccountId())
                .orElseThrow();

        if (sourceAccount.getBalance().compareTo(request.amount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        sourceAccount.withdraw(request.amount());
        destinationAccount.deposit(request.amount());

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                sourceAccount.getId(),
                destinationAccount.getId(),
                request.amount(),
                Instant.now()
        );

        transactionRepository.save(transaction);

        return new TransactionResponse(
                transaction.getId(),
                transaction.getSourceAccountId(),
                transaction.getDestinationAccountId(),
                transaction.getAmount(),
                transaction.getCreatedAt()
        );
    }

    public List<TransactionResponse> getTransactions(UUID accountId) {
        List<Transaction> transactions =
                transactionRepository.findByAccountId(accountId);

        List<TransactionResponse> responses = new ArrayList<>();

        for (Transaction transaction : transactions) {
            responses.add(new TransactionResponse(
                    transaction.getId(),
                    transaction.getSourceAccountId(),
                    transaction.getDestinationAccountId(),
                    transaction.getAmount(),
                    transaction.getCreatedAt()
            ));
        }

        return responses;
    }
}