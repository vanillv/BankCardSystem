package com.bank.card.repository;

import com.bank.card.model.entity.Card;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("SELECT c FROM Card c WHERE c.user.id = :userId")
    List<Card> findAllByUserId(@Param("userId") Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Card c WHERE c.id = :id")
    Optional<Card> findByIdWithLock(@Param("id") Long id);

    @Query("""
        SELECT COUNT(c) > 0 FROM Card c 
        WHERE c.id = :cardId AND c.user.id = :userId
        """)
    boolean existsByIdAndUserId(@Param("cardId") Long cardId, 
                               @Param("userId") Long userId);

    @Query("SELECT c FROM Card c WHERE c.cardNumber = :cardNumber")
    Optional<Card> findByCardNumber(@Param("cardNumber") String cardNumber);
}