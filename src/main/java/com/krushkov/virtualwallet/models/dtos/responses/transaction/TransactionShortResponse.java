package com.krushkov.virtualwallet.models.dtos.responses.transaction;

import com.krushkov.virtualwallet.models.enums.TransactionStatus;
import com.krushkov.virtualwallet.models.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionShortResponse(
        Long id,
        String label,

        TransactionType type,
        TransactionStatus status,

        BigDecimal amount,
        String currencyCode,

        Long senderWalletId,
        Long recipientWalletId,

        LocalDateTime createdAt
) {}
