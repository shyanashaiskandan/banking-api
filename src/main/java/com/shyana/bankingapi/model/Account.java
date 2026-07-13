package com.shyana.bankingapi.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Account {

    private final UUID id;
    private final String ownerName;
    private BigDecimal balance;
    private final Instant createdAt;

    public Account(
            UUID id,
            String ownerName,
            BigDecimal balance,
            Instant createdAt
    ) {
        this.id = id;
        this.ownerName = ownerName;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}