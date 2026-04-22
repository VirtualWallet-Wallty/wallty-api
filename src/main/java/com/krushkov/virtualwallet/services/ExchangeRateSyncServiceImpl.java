package com.krushkov.virtualwallet.services;

import com.krushkov.virtualwallet.external.ExchangeRateApiResponse;
import com.krushkov.virtualwallet.external.ExchangeRateClient;
import com.krushkov.virtualwallet.models.Currency;
import com.krushkov.virtualwallet.models.ExchangeRate;
import com.krushkov.virtualwallet.repositories.CurrencyRepository;
import com.krushkov.virtualwallet.repositories.ExchangeRateRepository;
import com.krushkov.virtualwallet.services.contracts.CurrencyService;
import com.krushkov.virtualwallet.services.contracts.ExchangeRateSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeRateSyncServiceImpl implements ExchangeRateSyncService {
    
    private final ExchangeRateClient exchangeRateClient;
    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;
    
    private static final int SCALE = 8;
    private static final String DEFAULT_BASE_CURRENCY_CODE = "EUR";

    @Override
    @Transactional
    public void syncRates(Currency baseCurrency) {
        String baseCurrencyCode = baseCurrency.getCode().trim().toUpperCase();

        ExchangeRateApiResponse response = exchangeRateClient.fetchRates(baseCurrencyCode);

        LocalDateTime timestamp = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(response.lastUpdateUnix()),
                ZoneOffset.UTC
        );
        
        for (Map.Entry<String, BigDecimal> entry : response.rates().entrySet()) {
            String targetCurrencyCode = entry.getKey().trim().toUpperCase();
            if (baseCurrencyCode.equals(targetCurrencyCode)) {
                continue;
            }

            BigDecimal rate = entry.getValue();

            save(baseCurrencyCode, targetCurrencyCode, rate, timestamp);
            
            if (rate.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal reverse = BigDecimal.ONE.divide(rate, SCALE, RoundingMode.HALF_UP);
                save(targetCurrencyCode, baseCurrencyCode, reverse, timestamp);
            }
        }
    }

    @Scheduled(fixedRate = 86400000)
    @Transactional
    public void syncRatesScheduled() {
        Currency baseCurrency = currencyRepository.findByCodeAndIsActiveTrue(DEFAULT_BASE_CURRENCY_CODE)
                .orElse(null);

        if (baseCurrency == null) {
            return;
        }

        syncRates(baseCurrency);
    }

    private void save(String fromCurrencyCode, String toCurrencyCode, BigDecimal rate, LocalDateTime timestamp) {
        if (fromCurrencyCode.equalsIgnoreCase(toCurrencyCode)) {
            return;
        }

        Currency fromCurrency = currencyRepository.findByCodeAndIsActiveTrue(fromCurrencyCode).orElse(null);
        Currency toCurrency = currencyRepository.findByCodeAndIsActiveTrue(toCurrencyCode).orElse(null);

        if (fromCurrency == null || toCurrency == null) {
            return;
        }

        ExchangeRate exchangeRate = exchangeRateRepository
                .findByFromCurrencyAndToCurrency(fromCurrency, toCurrency)
                .orElseGet(ExchangeRate::new);
        
        exchangeRate.setFromCurrency(fromCurrency);
        exchangeRate.setToCurrency(toCurrency);
        exchangeRate.setRate(rate);
        exchangeRate.setLastUpdated(timestamp);
        
        exchangeRateRepository.save(exchangeRate);
    }
}
