package com.krushkov.virtualwallet.repositories.specifications;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import com.krushkov.virtualwallet.models.Transaction;
import com.krushkov.virtualwallet.models.dtos.requests.transaction.TransactionFilterOptions;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class TransactionSpecifications {

    private TransactionSpecifications() {
    }

    public static Specification<Transaction> sender(Long userId) {
        return (root, query, cb) ->
                cb.equal(root.get("sender").get("id"), userId);
    }

    public static Specification<Transaction> recipient(Long userId) {
        return (root, query, cb) ->
                cb.equal(root.get("recipient").get("id"), userId);
    }

    public static Specification<Transaction> principal(Long principalId) {
        return (root, query, cb) ->
                cb.or(
                        cb.equal(root.get("sender").get("id"), principalId),
                        cb.equal(root.get("recipient").get("id"), principalId)
                );
    }

    public static Specification<Transaction> withFilters(TransactionFilterOptions filters) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filters.label() != null && !filters.label().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("label")),
                        "%" + filters.label().trim().toLowerCase() + "%")
                );
            }

            if (filters.senderWalletId() != null) {
                predicates.add(cb.equal(
                        root.join("senderWallet", JoinType.LEFT).get("id"),
                        filters.senderWalletId())
                );
            }

            if (filters.recipientWalletId() != null) {
                predicates.add(cb.equal(
                        root.join("recipientWallet", JoinType.LEFT).get("id"),
                        filters.recipientWalletId())
                );
            }

            if (filters.type() != null) {
                predicates.add(cb.equal(root.get("type"),
                        filters.type())
                );
            }

            if (filters.status() != null) {
                predicates.add(cb.equal(root.get("status"),
                        filters.status())
                );
            }

            if (filters.senderCurrencyCode() != null && !filters.senderCurrencyCode().isBlank()) {
                predicates.add(cb.equal(
                        cb.upper(root.get("senderCurrency").get("code")),
                        filters.senderCurrencyCode().trim().toUpperCase())
                );
            }

            if (filters.recipientCurrencyCode() != null && !filters.recipientCurrencyCode().isBlank()) {
                predicates.add(cb.equal(
                        cb.upper(root.get("recipientCurrency").get("code")),
                        filters.recipientCurrencyCode().trim().toUpperCase())
                );
            }

            if (filters.externalReference() != null && !filters.externalReference().isBlank()) {
                predicates.add(cb.equal(
                        cb.upper(root.get("externalReference")),
                        filters.externalReference().trim().toUpperCase())
                );
            }

            if (filters.minSenderAmount() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("senderAmount"),
                        filters.minSenderAmount())
                );
            }

            if (filters.maxSenderAmount() != null) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("senderAmount"),
                        filters.maxSenderAmount())
                );
            }

            if (filters.minRecipientAmount() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("recipientAmount"),
                        filters.minRecipientAmount())
                );
            }

            if (filters.maxRecipientAmount() != null) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("recipientAmount"),
                        filters.maxRecipientAmount())
                );
            }

            if (filters.createdFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("createdAt"),
                        filters.createdFrom())
                );
            }

            if (filters.createdTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("createdAt"),
                        filters.createdTo())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
