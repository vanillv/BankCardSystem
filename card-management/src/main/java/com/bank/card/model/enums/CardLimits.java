package com.bank.card.model.enums;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardLimits {

    @Column(precision = 19, scale = 4)
    private BigDecimal dailyWithdrawalLimit;

    @Column(precision = 19, scale = 4)
    private BigDecimal monthlyWithdrawalLimit;

    @Column(precision = 19, scale = 4)
    private BigDecimal transactionLimit;
}
