package com.krushkov.virtualwallet.controllers;

import com.krushkov.virtualwallet.helpers.factories.ApiResponseFactory;
import com.krushkov.virtualwallet.helpers.mappers.TransactionMapper;
import com.krushkov.virtualwallet.models.dtos.requests.TransferRequest;
import com.krushkov.virtualwallet.models.dtos.responses.api.ApiResponse;
import com.krushkov.virtualwallet.models.dtos.responses.transaction.TransactionLongResponse;
import com.krushkov.virtualwallet.services.contacts.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<TransactionLongResponse>> transfer(@Valid @RequestBody TransferRequest request) {
        TransactionLongResponse transactionLongResponse = transactionMapper.toLong(transferService.transfer(request));
        return ApiResponseFactory.ok("Transfer successful.", transactionLongResponse);
    }
}
