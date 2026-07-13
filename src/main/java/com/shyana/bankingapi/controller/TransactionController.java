package com.shyana.bankingapi.controller;

import com.shyana.bankingapi.dto.TransactionRequest;
import com.shyana.bankingapi.dto.TransactionResponse;
import com.shyana.bankingapi.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions")
    public TransactionResponse createTransaction(
            @Valid @RequestBody TransactionRequest request) {
        return transactionService.createTransaction(request);
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public List<TransactionResponse> getTransactions(
            @PathVariable UUID accountId) {
        return transactionService.getTransactions(accountId);
    }
}