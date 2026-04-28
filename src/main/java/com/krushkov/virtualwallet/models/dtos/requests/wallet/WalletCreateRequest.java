package com.krushkov.virtualwallet.models.dtos.requests.wallet;

import com.krushkov.virtualwallet.helpers.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WalletCreateRequest(

        @NotBlank(message = ValidationMessages.WALLET_NAME_NOT_NULL_ERROR)
        @Size(min = 3, max = 16, message = ValidationMessages.WALLET_NAME_LENGTH_ERROR)
        String name,

        @NotBlank(message = ValidationMessages.CURRENCY_CODE_NOT_NULL_ERROR)
        @Size(min = 3, max = 3, message = ValidationMessages.CURRENCY_CODE_LENGTH_ERROR)
        String currencyCode
) {}
