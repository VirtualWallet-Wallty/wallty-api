package com.krushkov.virtualwallet.models.dtos.requests.card;

import com.krushkov.virtualwallet.helpers.ValidationMessages;
import jakarta.validation.constraints.*;

import java.time.YearMonth;

public record CardCreateRequest(

        @Size(min = 6, max = 100, message = ValidationMessages.CARD_HOLDER_LENGTH_ERROR)
        @NotBlank(message = ValidationMessages.CARD_HOLDER_NOT_NULL_ERROR)
        String cardHolder,

        @Pattern(regexp = "\\d{16}", message = ValidationMessages.CARD_NUMBER_LENGTH_ERROR)
        @NotBlank(message = ValidationMessages.CARD_NUMBER_NOT_NULL_ERROR)
        String cardNumber,

        @Min(1) @Max(12)
        @NotNull(message = ValidationMessages.CARD_EXPIRATION_MONTH_NOT_NULL_ERROR)
        Integer expirationMonth,

        @NotNull(message = ValidationMessages.CARD_EXPIRATION_YEAR_NOT_NULL_ERROR)
        Integer expirationYear
) {
    @AssertTrue(message = ValidationMessages.CARD_EXPIRED_ERROR)
    public boolean isExpirationDateValid() {
        if (expirationMonth == null || expirationYear == null) {
            return true;
        }

        YearMonth expiration = YearMonth.of(expirationYear, expirationMonth);
        return !expiration.isBefore(YearMonth.now());
    }
}
