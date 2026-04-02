package com.krushkov.virtualwallet.services.contacts;

import com.krushkov.virtualwallet.models.Currency;

import java.util.List;

public interface CurrencyService {

    List<Currency> getAllActive();

    Currency getByCode(String code);

    Currency create(Currency currency);

    void activate(String code);

    void deactivate(String code);
}
