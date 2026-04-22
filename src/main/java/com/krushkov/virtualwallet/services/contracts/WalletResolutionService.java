package com.krushkov.virtualwallet.services.contracts;

import com.krushkov.virtualwallet.models.Wallet;

public interface WalletResolutionService {

    Wallet resolveSenderWallet(Long userId, String currencyCode, Long sourceWalletId);

    Wallet resolveRecipientWallet(Long recipientId, String currencyCode);
}
