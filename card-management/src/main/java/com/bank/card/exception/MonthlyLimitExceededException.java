package com.bank.card.exception;

import java.math.BigDecimal;

public class MonthlyLimitExceededException extends RuntimeException {
    public MonthlyLimitExceededException(BigDecimal remaining) {
        super("Превышен месячный лимит. Доступно: " + remaining);
    }
}