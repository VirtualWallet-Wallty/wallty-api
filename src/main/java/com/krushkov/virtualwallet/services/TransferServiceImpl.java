package com.krushkov.virtualwallet.services;

import com.krushkov.virtualwallet.helpers.validations.TransactionValidations;
import com.krushkov.virtualwallet.helpers.validations.UserValidations;
import com.krushkov.virtualwallet.models.Transaction;
import com.krushkov.virtualwallet.models.Wallet;
import com.krushkov.virtualwallet.models.dtos.requests.TransferRequest;
import com.krushkov.virtualwallet.security.auth.PrincipalContext;
import com.krushkov.virtualwallet.services.contacts.TransactionService;
import com.krushkov.virtualwallet.services.contacts.TransferService;
import com.krushkov.virtualwallet.services.contacts.WalletService;
import com.krushkov.virtualwallet.helpers.TransactionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final WalletService walletService;
    private final TransactionFactory transactionFactory;
    private final TransactionService transactionService;

    @Override
    @Transactional
    public Transaction transfer(TransferRequest request) {
        Long senderId = PrincipalContext.getId();

        Wallet senderWallet = walletService.getDefault(senderId);
        Wallet recipientWallet = walletService.getDefault(request.recipientId());

        TransactionValidations.validateSameCurrency(senderWallet, recipientWallet);

        walletService.debit(senderWallet.getId(), request.amount());
        walletService.creditRecipientWallet(recipientWallet.getId(), request.recipientId(), request.amount());

        return transactionService.create(
                transactionFactory.createTransfer(senderWallet, recipientWallet, request.amount())
        );
    }
}
