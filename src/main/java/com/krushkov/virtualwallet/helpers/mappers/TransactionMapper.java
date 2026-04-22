package com.krushkov.virtualwallet.helpers.mappers;

import com.krushkov.virtualwallet.models.Transaction;
import com.krushkov.virtualwallet.models.dtos.responses.transaction.TransactionLongResponse;
import com.krushkov.virtualwallet.models.dtos.responses.transaction.TransactionShortResponse;
import com.krushkov.virtualwallet.models.enums.TransactionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {UserMapper.class, WalletMapper.class, CurrencyMapper.class}
)
public interface TransactionMapper {

    @Mapping(target = "senderCurrencyCode", source = "transaction.senderCurrency.code")
    @Mapping(target = "recipientCurrencyCode", source = "transaction.recipientCurrency.code")
    @Mapping(target = "counterpartyName", expression = "java(resolveCounterpartyName(transaction, currentUserId))")
    TransactionShortResponse toShort(Transaction transaction, Long currentUserId);

    @Mapping(target = "counterpartyName", expression = "java(resolveCounterpartyName(transaction, currentUserId))")
    TransactionLongResponse toLong(Transaction transaction, Long currentUserId);

    default String resolveCounterpartyName(Transaction tx, Long currentUserId) {
        if (tx.getType() == TransactionType.PAYMENT) {
            return "MERCHANT";
        }
        if (tx.getType() == TransactionType.TOP_UP) {
            return "EXTERNAL";
        }

        if (tx.getType() == TransactionType.TRANSFER) {
            return "INTERNAL";
        }

            boolean isSender = tx.getSender() != null && tx.getSender().getId().equals(currentUserId);

        if (isSender) {
            if (tx.getRecipient() != null) {
                return tx.getRecipient().getUsername();
            }

            if (tx.getRecipientWallet() != null) {
                return tx.getRecipientWallet().getName();
            }
        } else {
            if (tx.getSender() != null) {
                return tx.getSender().getUsername();
            }

            if (tx.getSenderWallet() != null) {
                return tx.getSenderWallet().getName();
            }
        }
        return "UNKNOWN";
    }
}
