package com.krushkov.virtualwallet.controllers;

import com.krushkov.virtualwallet.helpers.mappers.TransactionMapper;
import com.krushkov.virtualwallet.models.dtos.requests.PaymentRequest;
import com.krushkov.virtualwallet.models.dtos.responses.transaction.TransactionLongResponse;
import com.krushkov.virtualwallet.services.contacts.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pay")
@RequiredArgsConstructor
public class PaymentController {

    private final TransactionMapper transactionMapper;
    private final PaymentService paymentService;

    @PostMapping
    public TransactionLongResponse topUp(@Valid @RequestBody PaymentRequest request) {
        return transactionMapper.toLong(paymentService.pay(request));
    }
}
