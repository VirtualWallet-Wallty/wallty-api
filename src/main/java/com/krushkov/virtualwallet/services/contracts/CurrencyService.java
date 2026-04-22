package com.krushkov.virtualwallet.services.contracts;

import com.krushkov.virtualwallet.models.Currency;

import java.util.List;

public interface CurrencyService {

    List<Currency> getAllActive();

    Currency getByCode(String targetCurrencyCode);

    Currency create(Currency currency);

    void activate(String targetCurrencyCode);

    void deactivate(String targetCurrencyCode);
}
