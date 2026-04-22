package com.krushkov.virtualwallet.services.contracts;

import com.krushkov.virtualwallet.models.Currency;

public interface ExchangeRateSyncService {

    void syncRates(Currency baseCurrency);
}
