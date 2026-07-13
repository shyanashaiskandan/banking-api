package com.shyana.bankingapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateAccountRequest(
        @NotBlank
        String ownerName,

        @NotNull
        @DecimalMin("0.00")
        BigDecimal initialBalance
) {
}