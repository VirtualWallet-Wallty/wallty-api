package com.krushkov.virtualwallet.repositories;

import jakarta.persistence.LockModeType;
import com.krushkov.virtualwallet.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository
        extends JpaRepository<Wallet, Long>, JpaSpecificationExecutor<Wallet> {

    Optional<Wallet> findByIdAndIsDeletedFalse(Long walletId);

    List<Wallet> findAllByUserIdAndIsDeletedFalse(Long userId);

    Optional<Wallet> findByIdAndUserIdAndIsDeletedFalse(Long walletId, Long userId);

    Optional<Wallet> findByUserIdAndIsDefaultTrueAndIsDeletedFalse(Long userId);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("""
            select w from Wallet w
            where w.id = :walletId
            and w.user.id = :userId
            and w.isDeleted = false
            """)
    Optional<Wallet> findWithLockByIdAndUserIdAndIsDeletedFalse(Long walletId, Long userId);

    @Query("""
            select w from Wallet w
            where w.id = :walletId
            and w.isDeleted = false
            """)
    Optional<Wallet> findWithLockByIdAndIsDeletedFalse(Long walletId);

    int countByUserIdAndIsDeletedFalse(Long userId);

    boolean existsByNameAndUserIdAndIsDeletedFalse(String cardName, Long userId);

}
