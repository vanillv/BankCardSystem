package com.bank.card.model.dto.response;

import com.bank.card.model.enums.CardStatus;

import java.math.BigDecimal;

public record CardResponse(
    Long id,
    String maskedCardNumber,
    CardStatus status,
    BigDecimal balance
) {}