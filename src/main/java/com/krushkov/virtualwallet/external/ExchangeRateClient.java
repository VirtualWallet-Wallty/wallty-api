package com.krushkov.virtualwallet.external;

public interface ExchangeRateClient {

    ExchangeRateApiResponse fetchRates(String baseCurrencyCode);
}
