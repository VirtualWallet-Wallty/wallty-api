package com.krushkov.virtualwallet.services.contacts;

import com.krushkov.virtualwallet.models.Wallet;
import com.krushkov.virtualwallet.models.dtos.requests.wallet.WalletFilterOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {

    Wallet getById(Long targetWalletId);

    Wallet getByIdAndUserId(Long targetWalletId, Long targetUserId);

    Wallet getDefault(Long targetUserId);

    Wallet getMyDefault();

    List<Wallet> getAllByUserId(Long targetUserId);

    List<Wallet> getMyAll();

    Page<Wallet> search(WalletFilterOptions filters, Pageable pageable);

    Wallet create(Wallet wallet);

    void setDefault(Long targetWalletId);

    void creditMyWallet(Long targetWalletId, BigDecimal amount);

    void creditRecipientWallet(Long targetWalletId, Long recipientId, BigDecimal amount);

    void debit(Long targetWalletId, BigDecimal amount);
}
