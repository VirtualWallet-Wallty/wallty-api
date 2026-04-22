package com.krushkov.virtualwallet.controllers;

import com.krushkov.virtualwallet.helpers.factories.ApiResponseFactory;
import com.krushkov.virtualwallet.helpers.mappers.ExchangeRateMapper;
import com.krushkov.virtualwallet.models.dtos.responses.ExchangeRateResponse;
import com.krushkov.virtualwallet.models.dtos.responses.api.ApiResponse;
import com.krushkov.virtualwallet.services.contracts.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/exchange-rates")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateMapper exchangeRateMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExchangeRateResponse>>> getAllRates() {
        List<ExchangeRateResponse> rates = exchangeRateService.getAllRates().stream()
                .map(exchangeRateMapper::toResponse)
                .toList();

        return ApiResponseFactory.ok(rates);
    }

    @GetMapping("/{baseCurrencyCode}")
    public ResponseEntity<ApiResponse<List<ExchangeRateResponse>>> getRates(@PathVariable String baseCurrencyCode) {
        List<ExchangeRateResponse> rates = exchangeRateService.getRates(baseCurrencyCode).stream()
                .map(exchangeRateMapper::toResponse)
                .toList();

        return ApiResponseFactory.ok(rates);
    }

    @GetMapping("/{fromCurrencyCode}/{toCurrencyCode}")
    public ResponseEntity<ApiResponse<BigDecimal>> getRate(
            @PathVariable String fromCurrencyCode,
            @PathVariable String toCurrencyCode
    ){
        BigDecimal rate = exchangeRateService.getRate(fromCurrencyCode, toCurrencyCode);
        return ApiResponseFactory.ok(rate);
    }

    @PostMapping("/sync/{baseCurrencyCode}")
    public ResponseEntity<ApiResponse<Void>> syncRates(@PathVariable String baseCurrencyCode) {
        exchangeRateService.syncRates(baseCurrencyCode);
        return ApiResponseFactory.noContent("Exchange rates synced successfully.");
    }
}
