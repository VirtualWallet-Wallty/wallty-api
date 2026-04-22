package com.krushkov.virtualwallet.helpers.mappers;

import com.krushkov.virtualwallet.models.ExchangeRate;
import com.krushkov.virtualwallet.models.dtos.responses.ExchangeRateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CurrencyMapper.class})
public interface ExchangeRateMapper {

    ExchangeRateResponse toResponse(ExchangeRate exchangeRate);
}
