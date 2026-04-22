package com.krushkov.virtualwallet.services.contracts;

import com.krushkov.virtualwallet.models.Currency;
import com.krushkov.virtualwallet.models.dtos.responses.currency.ConversionResult;

import java.math.BigDecimal;

public interface CurrencyConversionService {

    ConversionResult convert(BigDecimal amount, Currency fromCurrency, Currency toCurrency, int targetScale);
}
