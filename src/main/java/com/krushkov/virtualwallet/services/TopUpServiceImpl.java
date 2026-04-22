package com.krushkov.virtualwallet.services;

import com.krushkov.virtualwallet.helpers.validations.CardValidations;
import com.krushkov.virtualwallet.models.Card;
import com.krushkov.virtualwallet.models.Currency;
import com.krushkov.virtualwallet.models.Transaction;
import com.krushkov.virtualwallet.models.Wallet;
import com.krushkov.virtualwallet.models.dtos.requests.TopUpRequest;
import com.krushkov.virtualwallet.models.dtos.responses.currency.ConversionResult;
import com.krushkov.virtualwallet.security.auth.PrincipalContext;
import com.krushkov.virtualwallet.services.contracts.*;
import com.krushkov.virtualwallet.helpers.factories.TransactionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TopUpServiceImpl implements TopUpService {

    private final WalletService walletService;
    private final WalletResolutionService walletResolutionService;
    private final CurrencyConversionService currencyConversionService;
    private final CurrencyService currencyService;
    private final TransactionFactory transactionFactory;
    private final TransactionService transactionService;
    private final CardService cardService;

    @Override
    @Transactional
    public Transaction topUp(TopUpRequest request) {
        Long userId = PrincipalContext.getId();
        Wallet wallet = walletResolutionService.resolveSenderWallet(
                userId, request.currencyCode(), request.walletId()
        );

        Card card = cardService.getById(request.cardId());
        CardValidations.validateIsActive(card);

        Currency recipientCurrency = wallet.getCurrency();
        Currency senderCurrency = currencyService.getByCode(request.currencyCode().toUpperCase());

        BigDecimal amountToCredit;
        BigDecimal exchangeRate = BigDecimal.ONE;

        if (recipientCurrency.equals(senderCurrency)) {
            amountToCredit = request.amount();
        } else {
            ConversionResult conversionResult = currencyConversionService.convert(
                    request.amount(),
                    senderCurrency,
                    recipientCurrency,
                    wallet.getCurrency().getDecimals()
            );

            amountToCredit = conversionResult.recipientAmount();
            exchangeRate = conversionResult.rate();
        }

        walletService.creditMyWallet(wallet.getId(), amountToCredit);

        return transactionService.create(
                transactionFactory.createTopUp(
                        wallet,
                        request.amount(),
                        senderCurrency,
                        amountToCredit,
                        recipientCurrency,
                        exchangeRate,
                        card.getCardSuffix(),
                        request.externalReference()
                )
        );
    }
}
