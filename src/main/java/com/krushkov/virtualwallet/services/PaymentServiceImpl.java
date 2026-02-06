package com.krushkov.virtualwallet.services;

import com.krushkov.virtualwallet.helpers.validations.UserValidations;
import com.krushkov.virtualwallet.models.Transaction;
import com.krushkov.virtualwallet.models.Wallet;
import com.krushkov.virtualwallet.models.dtos.requests.PaymentRequest;
import com.krushkov.virtualwallet.security.auth.PrincipalContext;
import com.krushkov.virtualwallet.services.contacts.PaymentService;
import com.krushkov.virtualwallet.services.contacts.TransactionService;
import com.krushkov.virtualwallet.services.contacts.WalletService;
import com.krushkov.virtualwallet.helpers.TransactionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final WalletService walletService;
    private final TransactionFactory transactionFactory;
    private final TransactionService transactionService;

    @Override
    @Transactional
    public Transaction pay(PaymentRequest request) {
        Long userId = PrincipalContext.getId();
        Wallet wallet = walletService.getDefault(userId);

        walletService.debit(wallet.getId(), request.amount());

        return transactionService.create(
                transactionFactory.createPayment(wallet, request.amount(), request.merchantReference())
        );
    }
}
