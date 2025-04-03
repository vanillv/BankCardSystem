package com.bank.card.exception;

import com.bank.card.model.enums.CardStatus;
public class CardBlockedException extends RuntimeException {
    public CardBlockedException(CardStatus status) {
        super("Карта заблокирована. Текущий статус: " + status);
    }
}