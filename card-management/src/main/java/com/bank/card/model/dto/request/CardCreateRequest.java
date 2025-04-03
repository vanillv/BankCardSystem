package com.bank.card.model.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CardCreateRequest(
    @NotBlank String cardHolderName,
    @Future LocalDate expirationDate,
    @Positive BigDecimal initialBalance
) {}