package com.krushkov.virtualwallet.controllers;

import com.krushkov.virtualwallet.helpers.factories.ApiResponseFactory;
import com.krushkov.virtualwallet.helpers.mappers.CurrencyMapper;
import com.krushkov.virtualwallet.models.Currency;
import com.krushkov.virtualwallet.models.dtos.requests.currency.CurrencyCreateRequest;
import com.krushkov.virtualwallet.models.dtos.responses.api.ApiResponse;
import com.krushkov.virtualwallet.models.dtos.responses.currency.CurrencyLongResponse;
import com.krushkov.virtualwallet.models.dtos.responses.currency.CurrencyShortResponse;
import com.krushkov.virtualwallet.services.contacts.CurrencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;
    private final CurrencyMapper currencyMapper;

    @GetMapping("/{targetCurrencyCode}")
    public ResponseEntity<ApiResponse<CurrencyLongResponse>> getByCode(@PathVariable String code) {
        CurrencyLongResponse currencyLongResponse = currencyMapper.toLong(currencyService.getByCode(code));
        return ApiResponseFactory.ok(currencyLongResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CurrencyShortResponse>>> getAllActive() {
        List<CurrencyShortResponse> currencyShortResponseList = currencyService.getAllActive().stream()
                .map(currencyMapper::toShort)
                .toList();

        return ApiResponseFactory.ok(currencyShortResponseList);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CurrencyLongResponse>> create(
            @Valid @RequestBody CurrencyCreateRequest request
    ) {
        Currency currency = currencyMapper.fromCreate(request);
        CurrencyLongResponse currencyLongResponse = currencyMapper.toLong(currencyService.create(currency));
        return ApiResponseFactory.ok("Currency created successfully.", currencyLongResponse);
    }

    @PatchMapping("/{targetCurrencyCode}/activate")
    public ResponseEntity<ApiResponse<Void>> activate(@PathVariable String targetCurrencyCode) {
        currencyService.activate(targetCurrencyCode);
        return ApiResponseFactory.noContent("Currency activated successfully.");
    }

    @PatchMapping("/{targetCurrencyCode}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivate(@PathVariable String targetCurrencyCode) {
        currencyService.deactivate(targetCurrencyCode);
        return ApiResponseFactory.noContent("Currency deactivated successfully.");
    }

}
