package com.krushkov.virtualwallet.helpers.factories;

import com.krushkov.virtualwallet.models.Currency;
import com.krushkov.virtualwallet.models.Transaction;
import com.krushkov.virtualwallet.models.Wallet;
import com.krushkov.virtualwallet.models.enums.TransactionStatus;
import com.krushkov.virtualwallet.models.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionFactory {

    public Transaction createPayment(
            Wallet senderWallet,
            BigDecimal senderAmount,
            Currency senderCurrency,
            BigDecimal recipientAmount,
            Currency recipientCurrency,
            BigDecimal exchangeRate,
            String externalReference
    ) {
        Transaction tx = base(senderAmount, senderCurrency, recipientAmount, recipientCurrency);
        tx.setType(TransactionType.PAYMENT);

        tx.setSenderWallet(senderWallet);
        tx.setSender(senderWallet.getUser());

        tx.setExchangeRate(exchangeRate);

        tx.setExternalReference(externalReference);

        tx.setLabel(senderWallet.getName() + " → MERCHANT");
        return tx;
    }

    public Transaction createTopUp(
            Wallet recipientWallet,
            BigDecimal senderAmount,
            Currency senderCurrency,
            BigDecimal recipientAmount,
            Currency recipientCurrency,
            BigDecimal exchangeRate,
            String cardSuffix,
            String externalReference
    ) {
        Transaction tx = base(senderAmount, senderCurrency, recipientAmount, recipientCurrency);
        tx.setType(TransactionType.TOP_UP);

        tx.setRecipientWallet(recipientWallet);
        tx.setRecipient(recipientWallet.getUser());

        tx.setExternalReference(externalReference);

        tx.setExchangeRate(exchangeRate);

        tx.setLabel("FROM ⋅⋅" + cardSuffix);
        return tx;
    }

    public Transaction createTransfer(
            Wallet senderWallet,
            Wallet recipientWallet,

            BigDecimal senderAmount,
            Currency senderCurrency,

            BigDecimal recipientAmount,
            Currency recipientCurrency,

            BigDecimal exchangeRate
    ) {
        Transaction tx = base(senderAmount, senderCurrency, recipientAmount, recipientCurrency);
        tx.setType(TransactionType.TRANSFER);

        tx.setSenderWallet(senderWallet);
        tx.setRecipientWallet(recipientWallet);

        tx.setSender(senderWallet.getUser());
        tx.setRecipient(recipientWallet.getUser());

        tx.setExchangeRate(exchangeRate);

        if (senderWallet.getUser().getId().equals(recipientWallet.getUser().getId())) {
            tx.setLabel(senderWallet.getName() + " → " + recipientWallet.getName());
        } else {
            tx.setLabel(senderWallet.getUser().getUsername() + " → " + recipientWallet.getUser().getUsername());
        }

        return tx;
    }

    private Transaction base(
            BigDecimal senderAmount,
            Currency senderCurrency,
            BigDecimal recipientAmount,
            Currency recipientCurrency
    ){
        Transaction tx = new Transaction();

        tx.setSenderAmount(senderAmount);
        tx.setSenderCurrency(senderCurrency);

        tx.setRecipientAmount(recipientAmount);
        tx.setRecipientCurrency(recipientCurrency);

        tx.setStatus(TransactionStatus.CONFIRMED);

        return tx;
    }
}
