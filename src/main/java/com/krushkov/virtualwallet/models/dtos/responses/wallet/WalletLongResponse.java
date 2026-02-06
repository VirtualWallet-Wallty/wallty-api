package com.krushkov.virtualwallet.models.dtos.responses.wallet;

import com.krushkov.virtualwallet.models.dtos.responses.currency.CurrencyShortResponse;
import com.krushkov.virtualwallet.models.dtos.responses.user.UserShortResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WalletLongResponse(
        Long id,
        UserShortResponse owner,

        String name,
        BigDecimal balance,
        CurrencyShortResponse currency,

        Long version,
        Boolean isDefault,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
