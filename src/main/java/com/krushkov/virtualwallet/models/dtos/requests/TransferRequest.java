package com.krushkov.virtualwallet.models.dtos.requests;

import com.krushkov.virtualwallet.helpers.ValidationMessages;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequest(

        @DecimalMin(value = "0.01", inclusive = true, message = ValidationMessages.AMOUNT_LENGTH_ERROR)
        @Digits(integer = 15, fraction = 2)
        @NotNull(message = ValidationMessages.AMOUNT_NOT_NULL_ERROR)
        BigDecimal amount,

        @NotNull(message = ValidationMessages.RECIPIENT_ID_NOT_NULL_ERROR)
        Long recipientId
) {}
