package com.bank.card.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferRequest(
        @NotNull Long sourceCardId,
        @NotNull Long targetCardId,
        @DecimalMin("0.01") BigDecimal amount
) {}
