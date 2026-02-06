package com.krushkov.virtualwallet.controllers;

import com.krushkov.virtualwallet.helpers.mappers.TransactionMapper;
import com.krushkov.virtualwallet.models.dtos.requests.TransferRequest;
import com.krushkov.virtualwallet.models.dtos.responses.transaction.TransactionLongResponse;
import com.krushkov.virtualwallet.services.contacts.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransactionMapper transactionMapper;
    private final TransferService transferService;

    @PostMapping
    public TransactionLongResponse topUp(@Valid @RequestBody TransferRequest request) {
        return transactionMapper.toLong(transferService.transfer(request));
    }
}
