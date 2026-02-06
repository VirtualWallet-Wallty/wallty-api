package com.krushkov.virtualwallet.repositories;

import com.krushkov.virtualwallet.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    @Query("""
            select t from Transaction t
            where t.id = :transactionId
            and (t.sender.id = :userId or t.recipient.id = :userId)
            """)
    Optional<Transaction> findAccessibleById(
            @Param("transactionId") Long transactionId,
            @Param("userId") Long userId
    );
}
