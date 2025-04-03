package com.bank.card.exception;

import java.math.BigDecimal;

public class DailyLimitExceededException extends RuntimeException {
    public DailyLimitExceededException(BigDecimal remaining) {
        super("Превышен дневной лимит. Доступно: " + remaining);
    }
}