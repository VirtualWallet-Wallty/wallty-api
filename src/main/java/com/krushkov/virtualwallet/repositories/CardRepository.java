package com.krushkov.virtualwallet.repositories;

import com.krushkov.virtualwallet.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByIdAndIsDeletedFalse(Long cardId);

    List<Card> findAllByUserIdAndIsDeletedFalse(Long userId);

    Optional<Card> findByIdAndUserIdAndIsDeletedFalse(Long cardId, Long userId);

    boolean existsByUserIdAndCardSuffixAndIsDeletedFalse(Long userId, String cardSuffix);
}
