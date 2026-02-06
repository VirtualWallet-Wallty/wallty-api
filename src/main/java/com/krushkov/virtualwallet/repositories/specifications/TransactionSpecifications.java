package com.krushkov.virtualwallet.repositories.specifications;

import jakarta.persistence.criteria.Predicate;
import com.krushkov.virtualwallet.models.Transaction;
import com.krushkov.virtualwallet.models.dtos.requests.transaction.TransactionFilterOptions;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class TransactionSpecifications {

    private TransactionSpecifications() {}

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

            if (filters.senderWalletId() != null) {
                predicates.add(cb.equal(root.get("senderWallet").get("id"), filters.senderWalletId()));
            }

            if (filters.recipientWalletId() != null) {
                predicates.add(cb.equal(root.get("recipientWallet").get("id"), filters.recipientWalletId()));
            }

            if (filters.type() != null) {
                predicates.add(cb.equal(root.get("type"), filters.type()));
            }

            if (filters.status() != null) {
                predicates.add(cb.equal(root.get("status"), filters.status()));
            }

            if (filters.createdFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), filters.createdFrom()));
            }

            if (filters.createdTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), filters.createdTo()));
            }

            if (filters.minAmount() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("amount"), filters.minAmount()));
            }

            if (filters.maxAmount() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("amount"), filters.maxAmount()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
