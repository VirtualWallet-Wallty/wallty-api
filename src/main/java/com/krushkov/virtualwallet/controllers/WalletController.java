package com.krushkov.virtualwallet.controllers;

import com.krushkov.virtualwallet.helpers.mappers.WalletMapper;
import com.krushkov.virtualwallet.models.Wallet;
import com.krushkov.virtualwallet.models.dtos.requests.wallet.WalletCreateRequest;
import com.krushkov.virtualwallet.models.dtos.requests.wallet.WalletFilterOptions;
import com.krushkov.virtualwallet.models.dtos.requests.TopUpRequest;
import com.krushkov.virtualwallet.models.dtos.responses.wallet.WalletLongResponse;
import com.krushkov.virtualwallet.models.dtos.responses.wallet.WalletShortResponse;
import com.krushkov.virtualwallet.services.contacts.TopUpService;
import com.krushkov.virtualwallet.services.contacts.TransactionService;
import com.krushkov.virtualwallet.services.contacts.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final WalletMapper walletMapper;

    @GetMapping("/{targetWalletId}")
    public WalletLongResponse getById(@PathVariable Long targetWalletId) {
        return walletMapper.toLong(walletService.getById(targetWalletId));
    }

    @GetMapping("/default")
    public WalletLongResponse getMyDefault() {
        return walletMapper.toLong(walletService.getMyDefault());
    }

    @GetMapping("/users/{targetUserId}")
    public List<WalletShortResponse> getAllByUserId(@PathVariable Long targetUserId) {
        return walletService.getAllByUserId(targetUserId).stream()
                .map(walletMapper::toShort)
                .toList();
    }

    @GetMapping("/my")
    public List<WalletShortResponse> getMyAll() {
        return walletService.getMyAll().stream()
                .map(walletMapper::toShort)
                .toList();
    }

    @GetMapping
    public Page<WalletShortResponse> search(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String currencyCode,
            @RequestParam(required = false) BigDecimal minBalance,
            @RequestParam(required = false) BigDecimal maxBalance,
            Pageable pageable
    ) {
        WalletFilterOptions filters = new WalletFilterOptions(
                userId,
                currencyCode,
                minBalance, maxBalance
        );

        return walletService.search(filters, pageable)
                .map(walletMapper::toShort);
    }

    @PostMapping
    public WalletLongResponse create(@Valid @RequestBody WalletCreateRequest request) {
        Wallet wallet = walletService.create(walletMapper.fromCreate(request));
        return walletMapper.toLong(wallet);
    }

    @PatchMapping("/{targetWalletId}/set-default")
    public void setDefault(@PathVariable Long targetWalletId) {
        walletService.setDefault(targetWalletId);
    }
}
