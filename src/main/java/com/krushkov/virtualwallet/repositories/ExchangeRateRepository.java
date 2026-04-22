package com.krushkov.virtualwallet.repositories;

import com.krushkov.virtualwallet.models.Currency;
import com.krushkov.virtualwallet.models.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findByFromCurrencyAndToCurrency(Currency from, Currency to);

    List<ExchangeRate> findAllByFromCurrency(Currency from);
}
