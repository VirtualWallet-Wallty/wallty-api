package com.krushkov.virtualwallet.services;

import com.krushkov.virtualwallet.models.Currency;
import com.krushkov.virtualwallet.models.dtos.responses.currency.ConversionResult;
import com.krushkov.virtualwallet.services.contracts.CurrencyConversionService;
import com.krushkov.virtualwallet.services.contracts.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final ExchangeRateService exchangeRateService;

    @Override
    public ConversionResult convert(BigDecimal amount, Currency fromCurrency, Currency toCurrency, int targetScale) {

        BigDecimal rate = exchangeRateService.getRate(fromCurrency.getCode(), toCurrency.getCode());
        BigDecimal rawResult = amount.multiply(rate);
        BigDecimal finalAmount = rawResult.setScale(targetScale, RoundingMode.HALF_UP);

        return new ConversionResult(amount, fromCurrency, finalAmount, toCurrency, rate);
    }
}
