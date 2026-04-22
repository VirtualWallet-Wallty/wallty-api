package com.krushkov.virtualwallet.models.dtos.responses.currency;

import com.krushkov.virtualwallet.models.Currency;

import java.math.BigDecimal;

public record ConversionResult(
        BigDecimal senderAmount,
        Currency senderCurrency,

        BigDecimal recipientAmount,
        Currency recipientCurrency,

        BigDecimal rate
) {}
