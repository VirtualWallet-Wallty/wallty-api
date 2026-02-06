package com.krushkov.virtualwallet.models.dtos.responses.card;

import com.krushkov.virtualwallet.models.enums.CardStatus;

public record CardShortResponse(
        Long id,
        Long ownerId,

        String cardSuffix,
        CardStatus status
) {}
