package com.krushkov.virtualwallet.controllers;

import com.krushkov.virtualwallet.helpers.factories.ApiResponseFactory;
import com.krushkov.virtualwallet.helpers.mappers.TransactionMapper;
import com.krushkov.virtualwallet.models.dtos.requests.TopUpRequest;
import com.krushkov.virtualwallet.models.dtos.responses.api.ApiResponse;
import com.krushkov.virtualwallet.models.dtos.responses.transaction.TransactionLongResponse;
import com.krushkov.virtualwallet.services.contacts.TopUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/top-up")
@RequiredArgsConstructor
public class TopUpController {

    private final TopUpService topUpService;
    private final TransactionMapper transactionMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionLongResponse>> topUp(@Valid @RequestBody TopUpRequest request) {
        TransactionLongResponse transactionLongResponse = transactionMapper.toLong(topUpService.topUp(request));
        return ApiResponseFactory.ok("Top-Up successful.", transactionLongResponse);
    }
}
