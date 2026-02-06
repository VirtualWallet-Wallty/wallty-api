package com.krushkov.virtualwallet.models.dtos.responses.transaction;

import com.krushkov.virtualwallet.models.enums.TransactionStatus;
import com.krushkov.virtualwallet.models.enums.TransactionType;

import java.math.BigDecimal;

public record TransactionShortResponse(
        Long id,

        TransactionType type,
        TransactionStatus status,

        BigDecimal amount,
        String currencyCode,

        Long senderWalletId,
        Long recipientWalletId
) {}
