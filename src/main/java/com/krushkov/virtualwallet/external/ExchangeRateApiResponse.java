package com.krushkov.virtualwallet.external;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

public record ExchangeRateApiResponse(
        String result,

        @JsonProperty("time_last_update_unix")
        long lastUpdateUnix,

        @JsonProperty("base_code")
        String baseCode,

        @JsonProperty("conversion_rates")
        Map<String, BigDecimal> rates
) {}
