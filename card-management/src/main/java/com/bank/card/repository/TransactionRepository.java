package com.bank.card.repository;

import com.bank.card.model.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
        SELECT t 
        FROM Transaction t 
        WHERE t.sourceCard.id = :cardId 
           OR t.targetCard.id = :cardId
        ORDER BY t.timestamp DESC
        """)
    Page<Transaction> findAllByCardId(@Param("cardId") Long cardId,
                                      Pageable pageable);

    @Query("""
        SELECT t 
        FROM Transaction t 
        WHERE t.sourceCard.user.id = :userId 
           OR t.targetCard.user.id = :userId
        ORDER BY t.timestamp DESC
        """)
    Page<Transaction> findAllByUserId(@Param("userId") Long userId, 
                                    Pageable pageable);

    @Query("""
        SELECT SUM(t.amount) 
        FROM Transaction t 
        WHERE t.sourceCard.id = :cardId 
        AND t.timestamp BETWEEN :start AND :end
        """)
    Optional<BigDecimal> calculateSpentAmountForPeriod(
        @Param("cardId") Long cardId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
    @Query("""
        SELECT COALESCE(SUM(t.amount), 0)
        FROM Transaction t
        WHERE t.sourceCard.id = :cardId
        AND t.type = 'WITHDRAWAL'
        AND t.timestamp BETWEEN :start AND :end
        """)
    Optional<BigDecimal> calculateDailyWithdrawals(
            @Param("cardId") Long cardId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
        SELECT COALESCE(SUM(t.amount), 0)
        FROM Transaction t
        WHERE t.sourceCard.id = :cardId
        AND t.type = 'WITHDRAWAL'
        AND t.timestamp BETWEEN :start AND :end
        """)
    Optional<BigDecimal> calculateMonthlyWithdrawals(
            @Param("cardId") Long cardId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}