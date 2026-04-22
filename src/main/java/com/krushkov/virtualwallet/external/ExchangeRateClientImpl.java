package com.krushkov.virtualwallet.external;

import com.krushkov.virtualwallet.exceptions.InvalidOperationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class ExchangeRateClientImpl implements ExchangeRateClient {

    private final RestClient exchangeRateRestClient;

    @Value("${exchange.api.key}")
    private String apiKey;

    @Override
    public ExchangeRateApiResponse fetchRates(String baseCurrencyCode) {
        try {
            ExchangeRateApiResponse response = exchangeRateRestClient.get()
                    .uri("/{key}/latest/{base}", apiKey, baseCurrencyCode.toUpperCase())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        throw new InvalidOperationException("Invalid request to Exchange Rate API (4xx).");
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new InvalidOperationException("Exchange Rate API is unavailable (5xx).");
                    })
                    .body(ExchangeRateApiResponse.class);

            if (response == null || response.rates() == null) {
                throw new InvalidOperationException("Invalid exchange rate API response.");
            }

            if (!"success".equalsIgnoreCase(response.result())) {
                throw new InvalidOperationException("Exchange API returned failure.");
            }

            return response;
        } catch (ResourceAccessException e) {
            throw new InvalidOperationException("Cannot connect to exchange API.");
        }
    }
}
