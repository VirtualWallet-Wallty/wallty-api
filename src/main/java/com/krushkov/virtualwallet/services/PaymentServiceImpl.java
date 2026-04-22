package com.krushkov.virtualwallet.services;

import com.krushkov.virtualwallet.models.Currency;
import com.krushkov.virtualwallet.models.Transaction;
import com.krushkov.virtualwallet.models.Wallet;
import com.krushkov.virtualwallet.models.dtos.requests.PaymentRequest;
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
public class PaymentServiceImpl implements PaymentService {

    private final WalletService walletService;
    private final WalletResolutionService walletResolutionService;
    private final CurrencyConversionService currencyConversionService;
    private final CurrencyService currencyService;
    private final TransactionFactory transactionFactory;
    private final TransactionService transactionService;

    @Override
    @Transactional
    public Transaction pay(PaymentRequest request) {
        Long userId = PrincipalContext.getId();
        Wallet senderWallet;
        senderWallet = walletResolutionService.resolveSenderWallet(
                userId, request.currencyCode(), request.sourceWalletId()
        );

        Currency senderCurrency = senderWallet.getCurrency();
        Currency recipientCurrency = currencyService.getByCode(request.currencyCode().toUpperCase());

        BigDecimal amountToDebit;
        BigDecimal recipientAmount = request.amount();
        BigDecimal exchangeRate = BigDecimal.ONE;

        if (senderCurrency.equals(recipientCurrency)) {
            amountToDebit = recipientAmount;
        } else {
            ConversionResult conversionResult = currencyConversionService.convert(
                    request.amount(),
                    recipientCurrency,
                    senderCurrency,
                    senderWallet.getCurrency().getDecimals()
            );

            amountToDebit = conversionResult.recipientAmount();
            exchangeRate = conversionResult.rate();
        }

        walletService.debit(senderWallet.getId(), amountToDebit);

        return transactionService.create(
                transactionFactory.createPayment(
                        senderWallet,
                        amountToDebit,
                        senderCurrency,
                        recipientAmount,
                        recipientCurrency,
                        exchangeRate,
                        request.merchantReference()
                )
        );
    }
}
