package com.krushkov.virtualwallet.models.dtos.requests;

import com.krushkov.virtualwallet.helpers.ValidationMessages;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PaymentRequest(

        @DecimalMin(value = "0.01", inclusive = true, message = ValidationMessages.AMOUNT_LENGTH_ERROR)
        @Digits(integer = 15, fraction = 2)
        @NotNull(message = ValidationMessages.AMOUNT_NOT_NULL_ERROR)
        BigDecimal amount,

        @Size(min = 3, max = 3, message = ValidationMessages.CURRENCY_CODE_LENGTH_ERROR)
        @NotBlank(message = ValidationMessages.CURRENCY_CODE_NOT_NULL_ERROR)
        String currencyCode,

        String merchantReference,

        @Positive(message = ValidationMessages.WALLET_ID_LENGTH_ERROR)
        Long sourceWalletId
) {}
