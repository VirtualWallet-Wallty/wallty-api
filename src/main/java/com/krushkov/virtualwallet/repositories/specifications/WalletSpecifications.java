package com.krushkov.virtualwallet.repositories.specifications;

import jakarta.persistence.criteria.Predicate;
import com.krushkov.virtualwallet.models.Wallet;
import com.krushkov.virtualwallet.models.dtos.requests.wallet.WalletFilterOptions;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class WalletSpecifications {
    public static Specification<Wallet> withFilters(WalletFilterOptions filters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.userId() != null) {
                predicates.add(cb.equal(root.get("user").get("id"), filters.userId()));
            }

            if (filters.currencyCode() != null) {
                predicates.add(cb.equal(root.get("currency").get("code"), filters.currencyCode()));
            }

            if (filters.minBalance() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("balance"), filters.minBalance()));
            }

            if (filters.maxBalance() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("balance"), filters.maxBalance()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
