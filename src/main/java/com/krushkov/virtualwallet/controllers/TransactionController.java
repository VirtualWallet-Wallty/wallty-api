package com.krushkov.virtualwallet.controllers;

import com.krushkov.virtualwallet.helpers.factories.ApiResponseFactory;
import com.krushkov.virtualwallet.helpers.mappers.TransactionMapper;
import com.krushkov.virtualwallet.models.dtos.requests.transaction.TransactionFilterOptions;
import com.krushkov.virtualwallet.models.dtos.requests.PaymentRequest;
import com.krushkov.virtualwallet.models.dtos.requests.TransferRequest;
import com.krushkov.virtualwallet.models.dtos.responses.api.ApiResponse;
import com.krushkov.virtualwallet.models.dtos.responses.transaction.TransactionLongResponse;
import com.krushkov.virtualwallet.models.dtos.responses.transaction.TransactionShortResponse;
import com.krushkov.virtualwallet.models.enums.TransactionStatus;
import com.krushkov.virtualwallet.models.enums.TransactionType;
import com.krushkov.virtualwallet.services.contacts.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @GetMapping("/{targetTransactionId}")
    public ResponseEntity<ApiResponse<TransactionLongResponse>> getById(@PathVariable Long targetTransactionId) {
        TransactionLongResponse transactionLongResponse =
                transactionMapper.toLong(transactionService.getById(targetTransactionId));

        return ApiResponseFactory.ok(transactionLongResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TransactionShortResponse>>> search(
            @RequestParam(required = false) String label,
            @RequestParam(required = false) Long senderId,
            @RequestParam(required = false) Long recipientId,
            @RequestParam(required = false) Long senderWalletId,
            @RequestParam(required = false) Long recipientWalletId,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(required = false) LocalDateTime createdFrom,
            @RequestParam(required = false) LocalDateTime createdTo,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            Pageable pageable
    ) {
        TransactionFilterOptions filters = new TransactionFilterOptions(
                label,
                senderId, recipientId,
                senderWalletId, recipientWalletId,
                type, status,
                createdFrom, createdTo,
                minAmount, maxAmount
        );

        Page<TransactionShortResponse> transactionShortResponsePage = transactionService.search(filters, pageable)
                .map(transactionMapper::toShort);

        return ApiResponseFactory.ok(transactionShortResponsePage);
    }
}
