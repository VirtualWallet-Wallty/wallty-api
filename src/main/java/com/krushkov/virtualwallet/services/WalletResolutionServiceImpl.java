package com.krushkov.virtualwallet.services;

import com.krushkov.virtualwallet.exceptions.EntityNotFoundException;
import com.krushkov.virtualwallet.models.Wallet;
import com.krushkov.virtualwallet.repositories.WalletRepository;
import com.krushkov.virtualwallet.services.contracts.WalletResolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletResolutionServiceImpl implements WalletResolutionService {

    private final WalletRepository walletRepository;

    @Override
    public Wallet resolveSenderWallet(Long userId, String currencyCode, Long sourceWalletId) {
        if (sourceWalletId != null) {
            return walletRepository.findByIdAndUserIdAndIsDeletedFalse(sourceWalletId, userId)
                    .orElseThrow(() -> new EntityNotFoundException("Wallet", sourceWalletId));
        }

        List<Wallet> wallets = walletRepository.findAllByUserIdAndIsDeletedFalse(userId);

        List<Wallet> sameCurrency = wallets.stream()
                .filter(w -> w.getCurrency().getCode().equalsIgnoreCase(currencyCode))
                .toList();

        if (!sameCurrency.isEmpty()) {
            return pickBestWallet(sameCurrency);
        }

        return walletRepository.findByUserIdAndIsDefaultTrueAndIsDeletedFalse(userId)
                .orElseThrow(() -> new EntityNotFoundException("Wallet", "default", "user id = " + userId));
    }

    @Override
    public Wallet resolveRecipientWallet(Long recipientId, String currencyCode) {
        List<Wallet> wallets = walletRepository.findAllByUserIdAndIsDeletedFalse(recipientId);

        List<Wallet> sameCurrency = wallets.stream()
                .filter(w -> w.getCurrency().getCode().equalsIgnoreCase(currencyCode))
                .toList();

        if (!sameCurrency.isEmpty()) {
            return pickBestWallet(sameCurrency);
        }

        return walletRepository.findByUserIdAndIsDefaultTrueAndIsDeletedFalse(recipientId)
                .orElseThrow(() -> new EntityNotFoundException("Wallet", "default", "user id = " + recipientId));
    }

    private Wallet pickBestWallet(List<Wallet> wallets) {
        return wallets.stream().min(Comparator
                        .comparing(Wallet::isDefault).reversed()
                        .thenComparing(Wallet::getId))
                .orElseThrow();
    }
}
