package com.krushkov.virtualwallet.models.dtos.requests.currency;

import com.krushkov.virtualwallet.helpers.ValidationMessages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CurrencyCreateRequest(
        @Size(min = 3, max = 3, message = ValidationMessages.CURRENCY_CODE_LENGTH_ERROR)
        @NotBlank(message = ValidationMessages.CURRENCY_CODE_NOT_NULL_ERROR)
        String code,

        @Size(max = 50, message = ValidationMessages.CURRENCY_NAME_LENGTH_ERROR)
        @NotBlank(message = ValidationMessages.CURRENCY_NAME_NOT_NULL_ERROR)
        String name,

        @Size(min = 6, max = 100, message = ValidationMessages.CURRENCY_SYMBOL_LENGTH_ERROR)
        @NotBlank(message = ValidationMessages.CURRENCY_SYMBOL_NOT_NULL_ERROR)
        String symbol,

        @Min(value = 0, message = ValidationMessages.CURRENCY_MIN_DECIMALS_LENGTH_ERROR)
        @Min(value = 10, message = ValidationMessages.CURRENCY_MAX_DECIMALS_LENGTH_ERROR)
        @NotNull(message = ValidationMessages.CURRENCY_DECIMALS_NOT_NULL_ERROR)
        Integer decimals
) {
}
