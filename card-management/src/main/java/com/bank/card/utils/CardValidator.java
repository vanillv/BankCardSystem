package com.bank.card.utils;

import com.bank.card.exception.*;
import com.bank.card.model.entity.Card;
import com.bank.card.model.enums.CardStatus;
import com.bank.card.model.enums.TransactionType;
import com.bank.card.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Component
@RequiredArgsConstructor
public class CardValidator {
    private final TransactionRepository transactionRepository;

    public void validateTransaction(Card card, BigDecimal amount, TransactionType type) {
        validateCardActive(card);
        validateLimitsExist(card);
        switch (type) {
            case WITHDRAWAL -> validateWithdrawal(card, amount);
            case TRANSFER -> validateTransfer(card, amount);
        }
    }
    private void validateWithdrawal(Card card, BigDecimal amount) {
        validateBalance(card, amount);
        validateDailyLimit(card, amount);
        validateMonthlyLimit(card, amount);
    }

    private void validateTransfer(Card card, BigDecimal amount) {
        validateBalance(card, amount);
    }

    private void validateCardActive(Card card) {
        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new CardBlockedException(card.getStatus());
        }
    }

    private void validateLimitsExist(Card card) {
        if (card.getLimits() == null) {
            throw new CardLimitsNotSetException();
        }
    }

    private void validateBalance(Card card, BigDecimal amount) {
        if (card.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(card.getBalance());
        }
    }

    private void validateDailyLimit(Card card, BigDecimal amount) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDateTime.now();
        BigDecimal spent = transactionRepository
                .calculateDailyWithdrawals(card.getId(), start, end)
                .orElse(BigDecimal.ZERO);
        BigDecimal allowed = card.getLimits().getDailyWithdrawalLimit()
                .subtract(spent);
        if (amount.compareTo(allowed) > 0) {
            throw new DailyLimitExceededException(allowed);
        }
    }
    private void validateMonthlyLimit(Card card, BigDecimal amount) {
        YearMonth currentMonth = YearMonth.now();
        LocalDateTime start = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime end = currentMonth.atEndOfMonth().atTime(23, 59, 59);
        BigDecimal spent = transactionRepository
                .calculateMonthlyWithdrawals(card.getId(), start, end)
                .orElse(BigDecimal.ZERO);
        BigDecimal allowed = card.getLimits().getMonthlyWithdrawalLimit()
                .subtract(spent);
        if (amount.compareTo(allowed) > 0) {
            throw new MonthlyLimitExceededException(allowed);
        }
    }
}