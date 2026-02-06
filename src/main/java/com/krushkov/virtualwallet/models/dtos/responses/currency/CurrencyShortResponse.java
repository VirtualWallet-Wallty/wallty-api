package com.krushkov.virtualwallet.models.dtos.responses.currency;

public record CurrencyShortResponse(
        String code,

        String name,
        String symbol
) {}
