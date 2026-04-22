package com.krushkov.virtualwallet.services;

import com.krushkov.virtualwallet.exceptions.EntityNotFoundException;
import com.krushkov.virtualwallet.helpers.validations.UserValidations;
import com.krushkov.virtualwallet.models.Currency;
import com.krushkov.virtualwallet.models.ExchangeRate;
import com.krushkov.virtualwallet.repositories.ExchangeRateRepository;
import com.krushkov.virtualwallet.services.contracts.CurrencyService;
import com.krushkov.virtualwallet.services.contracts.ExchangeRateService;
import com.krushkov.virtualwallet.services.contracts.ExchangeRateSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateSyncService exchangeRateSyncService;
    private final CurrencyService currencyService;

    @Override
    public List<ExchangeRate> getAllRates() {
        return exchangeRateRepository.findAll();
    }

    @Override
    public List<ExchangeRate> getRates(String baseCurrencyCode) {
        Currency baseCurrency = currencyService.getByCode(baseCurrencyCode.trim().toUpperCase());

        return exchangeRateRepository.findAllByFromCurrency(baseCurrency);
    }

    @Override
    public BigDecimal getRate(String fromCurrencyCode, String toCurrencyCode) {
        Currency fromCurrency = currencyService.getByCode(fromCurrencyCode.toUpperCase());
        Currency toCurrency = currencyService.getByCode(toCurrencyCode.toUpperCase());

        if (fromCurrency.equals(toCurrency)) {
            return BigDecimal.ONE;
        }

        return exchangeRateRepository
                .findByFromCurrencyAndToCurrency(fromCurrency, toCurrency)
                .map(ExchangeRate::getRate)
                .orElseThrow(() ->
                        new EntityNotFoundException("Exchange rate", "", fromCurrencyCode + " -> " + toCurrencyCode)
                );
    }

    @Override
    public void syncRates(String baseCurrencyCode) {
        UserValidations.validateIsAdmin();
        Currency baseCurrency = currencyService.getByCode(baseCurrencyCode.trim().toUpperCase());
        exchangeRateSyncService.syncRates(baseCurrency);
    }
}
