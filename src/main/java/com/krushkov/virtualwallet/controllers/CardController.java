package com.krushkov.virtualwallet.controllers;

import com.krushkov.virtualwallet.helpers.factories.ApiResponseFactory;
import com.krushkov.virtualwallet.helpers.mappers.CardMapper;
import com.krushkov.virtualwallet.models.Card;
import com.krushkov.virtualwallet.models.dtos.requests.card.CardCreateRequest;
import com.krushkov.virtualwallet.models.dtos.responses.api.ApiResponse;
import com.krushkov.virtualwallet.models.dtos.responses.card.CardLongResponse;
import com.krushkov.virtualwallet.models.dtos.responses.card.CardShortResponse;
import com.krushkov.virtualwallet.services.contracts.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final CardMapper cardMapper;

    @GetMapping("/{targetCardId}")
    public ResponseEntity<ApiResponse<CardLongResponse>> getById(@PathVariable Long targetCardId) {
        CardLongResponse cardLongResponse = cardMapper.toLong(cardService.getById(targetCardId));
        return ApiResponseFactory.ok(cardLongResponse);
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<CardShortResponse>>> getMyAll() {
        List<CardShortResponse> cardShortResponseList = cardService.getAllMyCards().stream()
                .map(cardMapper::toShort)
                .toList();

        return ApiResponseFactory.ok(cardShortResponseList);
    }

    @GetMapping("/users/{targetUserId}")
    public ResponseEntity<ApiResponse<List<CardShortResponse>>> getAllByUserId(@PathVariable Long targetUserId) {
        List<CardShortResponse> cardShortResponseList = cardService.getAllByUserId(targetUserId).stream()
                .map(cardMapper::toShort)
                .toList();

        return ApiResponseFactory.ok(cardShortResponseList);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CardLongResponse>> add(@Valid @RequestBody CardCreateRequest request) {
        Card card = cardMapper.fromCreate(request);
        CardLongResponse cardLongResponse = cardMapper.toLong(cardService.add(card));
        return ApiResponseFactory.ok("Card added successfully.", cardLongResponse);
    }

    @PatchMapping("/{targetCardId}/activate")
    public ResponseEntity<ApiResponse<Void>> activate(@PathVariable Long targetCardId) {
        cardService.activate(targetCardId);
        return ApiResponseFactory.noContent("Card activated successfully.");
    }

    @PatchMapping("/{targetCardId}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivate(@PathVariable Long targetCardId) {
        cardService.deactivate(targetCardId);
        return ApiResponseFactory.noContent("Card deactivated successfully.");
    }

    @DeleteMapping("/{targetCardId}")
    public ResponseEntity<ApiResponse<Void>> remove(@PathVariable Long targetCardId) {
        cardService.remove(targetCardId);
        return ApiResponseFactory.noContent("Card deactivated successfully.");
    }
}
