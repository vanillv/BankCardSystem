package com.bank.card.model.entity;
import com.bank.card.model.CardLimits;
import com.bank.card.model.enums.CardStatus;
import com.bank.card.converter.CardNumberEncryptor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.Transaction;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "cards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = CardNumberEncryptor.class)
    @Column(nullable = false, unique = true)
    private String cardNumber;

    @Column(nullable = false)
    private String cardHolderName;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus status;

    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal balance;

    @Embedded
    private CardLimits limits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("timestamp DESC")
    private List<Transaction> transactions = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;
}
