package com.bank.card.exception;

public class CardLimitExceededException extends RuntimeException {
    public CardLimitExceededException(String limitType) {
        super("Превышен " + limitType + " лимит");
    }
}