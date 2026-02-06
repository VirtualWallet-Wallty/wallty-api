package com.krushkov.virtualwallet.models.dtos.responses.auth;

public record UserPrincipalResponse(
        Long id,
        String username,
        String role,
        Boolean isBlocked
) {}
