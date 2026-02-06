package com.krushkov.virtualwallet.helpers.validations;

import com.krushkov.virtualwallet.exceptions.InvalidOperationException;
import com.krushkov.virtualwallet.helpers.ValidationMessages;
import com.krushkov.virtualwallet.models.Wallet;
import com.krushkov.virtualwallet.repositories.WalletRepository;

public final class WalletValidations {

    private WalletValidations() {}

    public static void validateNameExists(WalletRepository walletRepository, String walletName, Long userId) {
        if (walletRepository.existsByNameAndUserIdAndIsDeletedFalse(walletName, userId)) {
            throw new InvalidOperationException(ValidationMessages.WALLET_NAME_ALREADY_EXISTS_ERROR);
        }
    }

    public static void validateAlreadyDefault(Wallet wallet) {
        if (wallet.isDefault()) {
            throw new InvalidOperationException(ValidationMessages.WALLET_ALREADY_DEFAULT_ERROR);
        }
    }
}
