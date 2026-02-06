package com.krushkov.virtualwallet.models.dtos.requests.transaction;

import com.krushkov.virtualwallet.helpers.ValidationMessages;
import com.krushkov.virtualwallet.models.enums.TransactionStatus;
import com.krushkov.virtualwallet.models.enums.TransactionType;
import jakarta.validation.constraints.AssertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionFilterOptions(
        Long senderId,
        Long recipientId,

        Long senderWalletId,
        Long recipientWalletId,

        TransactionType type,
        TransactionStatus status,

        LocalDateTime createdFrom,
        LocalDateTime createdTo,

        BigDecimal minAmount,
        BigDecimal maxAmount
) {
    @AssertTrue(message = ValidationMessages.TRANSACTION_CREATE_RANGE_ERROR)
    public boolean isValidCreateRange() {
        if (createdFrom == null || createdTo == null) {
            return true;
        }
        return !createdFrom.isAfter(createdTo);
    }

    @AssertTrue(message = ValidationMessages.TRANSACTION_AMOUNT_RANGE_ERROR)
    public boolean isValidAmountRange() {
        if (minAmount == null || maxAmount == null) {
            return true;
        }
        return minAmount.compareTo(maxAmount) <= 0;
    }

    public TransactionFilterOptions withoutUserId() {
        return new TransactionFilterOptions(
                null,
                null,
                senderWalletId,
                recipientWalletId,
                type,
                status,
                createdFrom,
                createdTo,
                minAmount,
                maxAmount
        );
    }
}
