package com.krushkov.virtualwallet.models.dtos.responses.user;

import com.krushkov.virtualwallet.models.dtos.responses.card.CardShortResponse;
import com.krushkov.virtualwallet.models.dtos.responses.wallet.WalletShortResponse;

import java.time.LocalDateTime;
import java.util.List;

public record UserLongResponse(
        Long id,
        String username,

        String firstName,
        String lastName,

        String email,
        String phoneNumber,
        String photoUrl,

        String role,

        Boolean isBlocked,

        LocalDateTime createdAt,
        LocalDateTime updatedAt,

        List<WalletShortResponse> wallets,
        List<CardShortResponse> cards
) {}
