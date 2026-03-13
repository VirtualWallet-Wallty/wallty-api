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

    public Transaction createTransfer(
            Wallet senderWallet,
            Wallet recipientWallet,
            BigDecimal amount
    ) {
        Transaction tx = base(amount, senderWallet.getCurrency());
        tx.setType(TransactionType.TRANSFER);
        tx.setSenderWallet(senderWallet);
        tx.setRecipientWallet(recipientWallet);
        tx.setSender(senderWallet.getUser());
        tx.setRecipient(recipientWallet.getUser());
        return tx;
    }

    public Transaction createTopUp(Wallet wallet, BigDecimal amount, String externalReference) {
        Transaction tx = base(amount, wallet.getCurrency());
        tx.setType(TransactionType.TOP_UP);
        tx.setRecipientWallet(wallet);
        tx.setRecipient(wallet.getUser());
        tx.setExternalReference(externalReference);
        return tx;
    }

    public Transaction createPayment(Wallet senderWallet, BigDecimal amount, String externalReference) {
        Transaction tx = base(amount, senderWallet.getCurrency());
        tx.setType(TransactionType.PAYMENT);
        tx.setSenderWallet(senderWallet);
        tx.setSender(senderWallet.getUser());
        tx.setExternalReference(externalReference);
        return tx;
    }

    private Transaction base(BigDecimal amount, Currency currency) {
        Transaction tx = new Transaction();
        tx.setAmount(amount);
        tx.setCurrency(currency);
        tx.setStatus(TransactionStatus.CONFIRMED);
        return tx;
    }
}
