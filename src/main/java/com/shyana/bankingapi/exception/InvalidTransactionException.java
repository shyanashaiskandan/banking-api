package com.shyana.bankingapi.exception;

public class InvalidTransactionException extends RuntimeException {

    public InvalidTransactionException() {
        super("Cannot transfer to the same account");
    }
}