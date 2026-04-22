package com.krushkov.virtualwallet.controllers;

import com.krushkov.virtualwallet.helpers.factories.ApiResponseFactory;
import com.krushkov.virtualwallet.helpers.mappers.TransactionMapper;
import com.krushkov.virtualwallet.models.dtos.requests.transaction.TransactionFilterOptions;
import com.krushkov.virtualwallet.models.dtos.responses.api.ApiResponse;
import com.krushkov.virtualwallet.models.dtos.responses.transaction.TransactionLongResponse;
import com.krushkov.virtualwallet.models.dtos.responses.transaction.TransactionShortResponse;
import com.krushkov.virtualwallet.models.enums.TransactionStatus;
import com.krushkov.virtualwallet.models.enums.TransactionType;
import com.krushkov.virtualwallet.security.auth.PrincipalContext;
import com.krushkov.virtualwallet.services.contracts.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Long userId = PrincipalContext.getId();
        TransactionLongResponse transactionLongResponse =
                transactionMapper.toLong(transactionService.getById(targetTransactionId), userId);

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
            @RequestParam(required = false) String senderCurrencyCode,
            @RequestParam(required = false) String recipientCurrencyCode,
            @RequestParam(required = false) BigDecimal minSenderAmount,
            @RequestParam(required = false) BigDecimal maxSenderAmount,
            @RequestParam(required = false) BigDecimal minRecipientAmount,
            @RequestParam(required = false) BigDecimal maxRecipientAmount,
            @RequestParam(required = false) String externalReference,
            @RequestParam(required = false) LocalDateTime createdFrom,
            @RequestParam(required = false) LocalDateTime createdTo,
            Pageable pageable
    ) {
        Long userId = PrincipalContext.getId();
        TransactionFilterOptions filters = new TransactionFilterOptions(
                label,
                senderId, recipientId,
                senderWalletId, recipientWalletId,
                type, status,
                senderCurrencyCode, recipientCurrencyCode,
                minSenderAmount, maxSenderAmount,
                minRecipientAmount, maxRecipientAmount,
                externalReference,
                createdFrom, createdTo

        );

        Page<TransactionShortResponse> transactionShortResponsePage = transactionService.search(filters, pageable)
                .map(tx -> transactionMapper.toShort(tx, userId));

        return ApiResponseFactory.ok(transactionShortResponsePage);
    }
}
