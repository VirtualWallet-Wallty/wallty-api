package com.krushkov.virtualwallet.models.dtos.responses.currency;

public record CurrencyLongResponse(
        String code,

        String name,
        String symbol,

        Integer decimals,
        Boolean isActive
) {}
