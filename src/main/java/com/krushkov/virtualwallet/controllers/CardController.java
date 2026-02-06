package com.krushkov.virtualwallet.controllers;

import com.krushkov.virtualwallet.helpers.mappers.CardMapper;
import com.krushkov.virtualwallet.models.Card;
import com.krushkov.virtualwallet.models.User;
import com.krushkov.virtualwallet.models.dtos.requests.card.CardCreateRequest;
import com.krushkov.virtualwallet.models.dtos.responses.card.CardLongResponse;
import com.krushkov.virtualwallet.models.dtos.responses.card.CardShortResponse;
import com.krushkov.virtualwallet.services.contacts.CardService;
import com.krushkov.virtualwallet.services.contacts.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final CardMapper cardMapper;

    @GetMapping("/{targetCardId}")
    public CardLongResponse getById(@PathVariable Long targetCardId) {
        return cardMapper.toLong(cardService.getById(targetCardId));
    }

    @GetMapping("/my")
    public List<CardShortResponse> getMyAll() {
        return cardService.getAllMyCards().stream()
                .map(cardMapper::toShort)
                .toList();
    }

    @GetMapping("/users/{targetUserId}")
    public List<CardShortResponse> getAllByUserId(@PathVariable Long targetUserId) {
        return cardService.getAllByUserId(targetUserId).stream()
                .map(cardMapper::toShort)
                .toList();
    }

    @PostMapping
    public CardLongResponse add(@Valid @RequestBody CardCreateRequest request) {
        Card card = cardMapper.fromCreate(request);
        return cardMapper.toLong(cardService.add(card));
    }

    @PatchMapping("/{targetCardId}/activate")
    public void activate(@PathVariable Long targetCardId) {
        cardService.activate(targetCardId);
    }

    @PatchMapping("/{targetCardId}/deactivate")
    public void deactivate(@PathVariable Long targetCardId) {
        cardService.deactivate(targetCardId);
    }

    @DeleteMapping("/{targetCardId}")
    public void remove(@PathVariable Long targetCardId) {
        cardService.remove(targetCardId);
    }
}
