package com.krushkov.virtualwallet.services;

import com.krushkov.virtualwallet.exceptions.InvalidOperationException;
import com.krushkov.virtualwallet.helpers.validations.TransactionValidations;
import com.krushkov.virtualwallet.models.Currency;
import com.krushkov.virtualwallet.models.Transaction;
import com.krushkov.virtualwallet.models.Wallet;
import com.krushkov.virtualwallet.models.dtos.requests.TransferRequest;
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
public class TransferServiceImpl implements TransferService {

    private final WalletService walletService;
    private final WalletResolutionService walletResolutionService;
    private final CurrencyConversionService currencyConversionService;
    private final CurrencyService currencyService;
    private final TransactionFactory transactionFactory;
    private final TransactionService transactionService;

    @Override
    @Transactional
    public Transaction transfer(TransferRequest request) {
        Long senderId = PrincipalContext.getId();

        TransactionValidations.validateAmount(request.amount());

        Wallet senderWallet = walletResolutionService.resolveSenderWallet(
                senderId,
                request.currencyCode(),
                request.sourceWalletId()
        );

        Wallet recipientWallet;

        if (request.targetWalletId() != null) {
            recipientWallet = walletService.getById(request.targetWalletId());
        } else {
            recipientWallet = walletResolutionService.resolveRecipientWallet(
                    request.recipientId(),
                    request.currencyCode()
            );
        }

        if (senderWallet.getId().equals(recipientWallet.getId())) {
            throw new InvalidOperationException("Cannot transfer to the same wallet.");
        }

        Currency senderCurrency = senderWallet.getCurrency();
        Currency recipientCurrency = recipientWallet.getCurrency();
        Currency requestCurrency = currencyService.getByCode(request.currencyCode().toUpperCase());

        BigDecimal senderAmount;
        BigDecimal recipientAmount;
        BigDecimal exchangeRate = BigDecimal.ONE;

        if (senderCurrency.equals(requestCurrency) && recipientCurrency.equals(requestCurrency)) {
            senderAmount = request.amount();
            recipientAmount = request.amount();
        } else {
            ConversionResult conversionResult = currencyConversionService.convert(
                    request.amount(),
                    requestCurrency,
                    recipientCurrency,
                    recipientWallet.getCurrency().getDecimals()
            );

            recipientAmount = conversionResult.recipientAmount();
            if (!senderCurrency.equals(recipientCurrency)) {
                exchangeRate = currencyConversionService.convert(
                        BigDecimal.ONE,
                        senderCurrency,
                        recipientCurrency,
                        recipientWallet.getCurrency().getDecimals()
                ).recipientAmount();
            }

            if (senderCurrency.equals(requestCurrency)) {
                senderAmount = request.amount();
            } else {
                ConversionResult senderConversion = currencyConversionService.convert(
                        request.amount(),
                        requestCurrency,
                        senderCurrency,
                        senderWallet.getCurrency().getDecimals()
                );

                senderAmount = senderConversion.recipientAmount();
            }
        }

        walletService.debit(senderWallet.getId(), senderAmount);

        if (recipientWallet.getUser().getId().equals(senderId)) {
            walletService.creditMyWallet(recipientWallet.getId(), recipientAmount);
        } else {
            walletService.creditRecipientWallet(recipientWallet.getId(), request.recipientId(), recipientAmount);
        }

        return transactionService.create(
                transactionFactory.createTransfer(
                        senderWallet,
                        recipientWallet,
                        senderAmount,
                        senderCurrency,
                        recipientAmount,
                        recipientCurrency,
                        exchangeRate
                )
        );
    }
}
