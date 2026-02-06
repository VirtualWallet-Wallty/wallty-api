package com.krushkov.virtualwallet.models.dtos.responses.wallet;

import java.math.BigDecimal;

public record WalletShortResponse(
        Long id,
        Long ownerId,

        String name,
        BigDecimal balance,
        String currencyCode,

        Boolean isDefault
) {}
