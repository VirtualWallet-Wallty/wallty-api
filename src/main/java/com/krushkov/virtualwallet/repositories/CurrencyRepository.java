package com.krushkov.virtualwallet.repositories;

import com.krushkov.virtualwallet.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {

    Optional<Currency> findByCodeAndIsActiveTrue(String currencyCode);

    List<Currency> findByIsActiveTrue();

}
