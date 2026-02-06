package com.krushkov.virtualwallet.helpers.mappers;

import com.krushkov.virtualwallet.models.Transaction;
import com.krushkov.virtualwallet.models.dtos.responses.transaction.TransactionLongResponse;
import com.krushkov.virtualwallet.models.dtos.responses.transaction.TransactionShortResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {UserMapper.class, WalletMapper.class, CurrencyMapper.class}
)
public interface TransactionMapper {

    @Mapping(target = "senderWalletId", source = "senderWallet.id")
    @Mapping(target = "recipientWalletId", source = "recipientWallet.id")
    TransactionShortResponse toShort(Transaction transaction);

    TransactionLongResponse toLong(Transaction transaction);
}
