package com.krushkov.virtualwallet.models.dtos.requests;

import com.krushkov.virtualwallet.helpers.ValidationMessages;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PaymentRequest(

        @DecimalMin(value = "0.01", inclusive = true, message = ValidationMessages.AMOUNT_LENGTH_ERROR)
        @Digits(integer = 15, fraction = 2)
        @NotNull(message = ValidationMessages.AMOUNT_NOT_NULL_ERROR)
        BigDecimal amount,

        @NotBlank(message = ValidationMessages.MERCHANT_NOT_NULL_ERROR)
        String merchantReference
) {}
