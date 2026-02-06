package com.krushkov.virtualwallet.repositories.specifications;

import jakarta.persistence.criteria.Predicate;
import com.krushkov.virtualwallet.models.User;
import com.krushkov.virtualwallet.models.dtos.requests.user.UserFilterOptions;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecifications {

    public static Specification<User> withFilters(UserFilterOptions filters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.username() != null) {
                predicates.add(
                        cb.like(cb.lower(root.get("username")), "%" + filters.username().toLowerCase() + "%")
                );
            }

            if (filters.firstName() != null) {
                predicates.add(
                        cb.like(cb.lower(root.get("firstName")), "%" + filters.firstName().toLowerCase() + "%")
                );
            }

            if (filters.lastName() != null) {
                predicates.add(
                        cb.like(cb.lower(root.get("lastName")), "%" + filters.lastName().toLowerCase() + "%")
                );
            }

            if (filters.email() != null) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + filters.email().toLowerCase() + "%"));
            }

            if (filters.phoneNumber() != null) {
                predicates.add(cb.like(root.get("phoneNumber"), "%" + filters.phoneNumber() + "%"));
            }

            if (filters.isBlocked() != null) {
                predicates.add(cb.equal(root.get("isBlocked"), filters.isBlocked()));
            }

            if (filters.createdFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), filters.createdFrom()));
            }

            if (filters.createdTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), filters.createdTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
