package com.shyana.bankingapi.controller;

import com.shyana.bankingapi.dto.TransactionRequest;
import com.shyana.bankingapi.dto.TransactionResponse;
import com.shyana.bankingapi.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public TransactionResponse createTransaction(
            @Valid @RequestBody TransactionRequest request) {
        return transactionService.createTransaction(request);
    }
}