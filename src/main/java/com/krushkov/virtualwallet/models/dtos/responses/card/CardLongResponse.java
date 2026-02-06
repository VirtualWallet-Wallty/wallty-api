package com.krushkov.virtualwallet.models.dtos.responses.card;

import com.krushkov.virtualwallet.models.dtos.responses.user.UserShortResponse;
import com.krushkov.virtualwallet.models.enums.CardStatus;

import java.time.LocalDateTime;

public record CardLongResponse(
        Long id,
        UserShortResponse user,

        String cardHolder,
        String cardSuffix,

        Integer expirationMonth,
        Integer expirationYear,

        CardStatus status,

        LocalDateTime createdAt
) {}
