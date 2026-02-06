package com.krushkov.virtualwallet.models.dtos.requests;

import com.krushkov.virtualwallet.helpers.ValidationMessages;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TopUpRequest(

        @DecimalMin(value = "0.01", inclusive = true, message = ValidationMessages.AMOUNT_LENGTH_ERROR)
        @Digits(integer = 15, fraction = 2)
        @NotNull(message = ValidationMessages.AMOUNT_NOT_NULL_ERROR)
        BigDecimal amount,

        @Positive(message = ValidationMessages.CARD_ID_LENGTH_ERROR)
        @NotNull(message = ValidationMessages.CARD_ID_NOT_NULL_ERROR)
        Long cardId
) {}
