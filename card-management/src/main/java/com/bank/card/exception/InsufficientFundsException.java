package com.bank.card.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(BigDecimal balance) {
        super("Недостаточно средств. Текущий баланс: " + balance);
    }
}