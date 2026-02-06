package com.krushkov.virtualwallet.models;

import com.krushkov.virtualwallet.models.enums.CardStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cards")

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "card_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "card_holder", nullable = false, length = 100)
    private String cardHolder;

    @Column(name = "card_suffix", nullable = false, length = 4)
    private String cardSuffix;

    @Column(name = "expiration_month", nullable = false)
    private Integer expirationMonth;

    @Column(name = "expiration_year", nullable = false)
    private Integer expirationYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private CardStatus status = CardStatus.ACTIVE;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
