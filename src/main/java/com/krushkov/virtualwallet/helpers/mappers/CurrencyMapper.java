package com.krushkov.virtualwallet.helpers.mappers;

import com.krushkov.virtualwallet.models.Currency;
import com.krushkov.virtualwallet.models.dtos.requests.currency.CurrencyCreateRequest;
import com.krushkov.virtualwallet.models.dtos.responses.currency.CurrencyLongResponse;
import com.krushkov.virtualwallet.models.dtos.responses.currency.CurrencyShortResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyShortResponse toShort(Currency currency);

    CurrencyLongResponse toLong(Currency currency);

    Currency fromCreate(CurrencyCreateRequest currencyCreateRequest);
}
