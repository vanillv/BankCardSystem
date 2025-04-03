package com.bank.card.exception;

public class CardLimitsNotSetException extends RuntimeException {
    public CardLimitsNotSetException() {
        super("Лимиты карты не установлены");
    }
}