package com.krushkov.virtualwallet.models.dtos.responses;

import com.krushkov.virtualwallet.models.dtos.responses.currency.CurrencyShortResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExchangeRateResponse(
        CurrencyShortResponse fromCurrency,
        CurrencyShortResponse toCurrency,
        BigDecimal rate,
        LocalDateTime lastUpdated
) {}
