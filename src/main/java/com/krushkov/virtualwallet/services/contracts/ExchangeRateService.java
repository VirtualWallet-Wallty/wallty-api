package com.krushkov.virtualwallet.services.contracts;

import com.krushkov.virtualwallet.models.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeRateService {

    List<ExchangeRate> getAllRates();

    List<ExchangeRate> getRates(String baseCurrencyCode);

    BigDecimal getRate(String fromCurrencyCode, String toCurrencyCode);

    void syncRates(String baseCurrencyCode);
}
