package com.krushkov.virtualwallet.services;

import com.krushkov.virtualwallet.exceptions.EntityDuplicateException;
import com.krushkov.virtualwallet.exceptions.EntityNotFoundException;
import com.krushkov.virtualwallet.helpers.validations.UserValidations;
import com.krushkov.virtualwallet.models.Currency;
import com.krushkov.virtualwallet.repositories.CurrencyRepository;
import com.krushkov.virtualwallet.services.contacts.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    public List<Currency> getAllActive() {
        return currencyRepository.findByIsActiveTrue();
    }

    @Override
    public Currency getByCode(String code) {
        return currencyRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException("Currency", "code", code));
    }

    @Override
    public Currency create(Currency currency) {
        UserValidations.validateIsAdmin();

        currency.setCode(currency.getCode().toUpperCase());

        if (currencyRepository.existsById(currency.getCode())) {
            throw new EntityDuplicateException("Currency", "code", currency.getCode());
        }

        return currencyRepository.save(currency);
    }

    @Override
    public void activate(String code) {
        UserValidations.validateIsAdmin();

        Currency currency = getByCode(code);
        currency.setActive(true);
        currencyRepository.save(currency);
    }

    @Override
    public void deactivate(String code) {
        UserValidations.validateIsAdmin();

        Currency currency = getByCode(code);
        currency.setActive(false);
        currencyRepository.save(currency);
    }
}
