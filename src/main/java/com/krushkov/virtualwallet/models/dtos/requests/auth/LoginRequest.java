package com.krushkov.virtualwallet.models.dtos.requests.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String identifier,
        @NotBlank String password
) {}
