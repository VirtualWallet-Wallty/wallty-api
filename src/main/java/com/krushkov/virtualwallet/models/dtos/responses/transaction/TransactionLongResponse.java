package com.krushkov.virtualwallet.models.dtos.responses.transaction;

import com.krushkov.virtualwallet.models.dtos.responses.currency.CurrencyShortResponse;
import com.krushkov.virtualwallet.models.dtos.responses.user.UserShortResponse;
import com.krushkov.virtualwallet.models.dtos.responses.wallet.WalletShortResponse;
import com.krushkov.virtualwallet.models.enums.TransactionStatus;
import com.krushkov.virtualwallet.models.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionLongResponse(
        Long id,

        TransactionType type,
        TransactionStatus status,

        BigDecimal amount,
        CurrencyShortResponse currency,

        UserShortResponse sender,
        UserShortResponse recipient,

        WalletShortResponse senderWallet,
        WalletShortResponse recipientWallet,

        String externalReference,

        LocalDateTime createdAt
) {}
