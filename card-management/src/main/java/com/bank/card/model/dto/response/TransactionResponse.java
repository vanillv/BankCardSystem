package com.bank.card.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
    LocalDateTime timestamp,
    BigDecimal amount,
    String description
) {}