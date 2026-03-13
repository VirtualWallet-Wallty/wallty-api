package com.krushkov.virtualwallet.services;

import com.krushkov.virtualwallet.helpers.validations.CardValidations;
import com.krushkov.virtualwallet.models.Card;
import com.krushkov.virtualwallet.models.Transaction;
import com.krushkov.virtualwallet.models.Wallet;
import com.krushkov.virtualwallet.models.dtos.requests.TopUpRequest;
import com.krushkov.virtualwallet.security.auth.PrincipalContext;
import com.krushkov.virtualwallet.services.contacts.CardService;
import com.krushkov.virtualwallet.services.contacts.TopUpService;
import com.krushkov.virtualwallet.services.contacts.TransactionService;
import com.krushkov.virtualwallet.services.contacts.WalletService;
import com.krushkov.virtualwallet.helpers.factories.TransactionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TopUpServiceImpl implements TopUpService {

    private final WalletService walletService;
    private final TransactionFactory transactionFactory;
    private final TransactionService transactionService;
    private final CardService cardService;

    @Override
    @Transactional
    public Transaction topUp(TopUpRequest request) {
        Long principalId = PrincipalContext.getId();
        Wallet wallet = walletService.getDefault(principalId);

        Card card = cardService.getById(request.cardId());
        CardValidations.validateIsActive(card);

        walletService.creditMyWallet(wallet.getId(), request.amount());

        return transactionService.create(
                transactionFactory.createTopUp(wallet, request.amount(), card.getCardSuffix())
        );
    }
}
