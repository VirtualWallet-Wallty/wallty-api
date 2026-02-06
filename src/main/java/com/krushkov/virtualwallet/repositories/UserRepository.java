package com.krushkov.virtualwallet.repositories;

import com.krushkov.virtualwallet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository
         extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByIdAndIsDeletedFalse(Long userId);

    Optional<User> findByUsernameAndIsDeletedFalse(String username);

    Optional<User> findByEmailAndIsDeletedFalse(String email);

    Optional<User> findByPhoneNumberAndIsDeletedFalse(String phoneNumber);

    boolean existsByUsernameAndIsDeletedFalse(String username);

    boolean existsByEmailAndIsDeletedFalse(String email);

    boolean existsByPhoneNumberAndIsDeletedFalse(String phoneNumber);

}
