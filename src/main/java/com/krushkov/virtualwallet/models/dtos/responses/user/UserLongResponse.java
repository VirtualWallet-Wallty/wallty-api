package com.krushkov.virtualwallet.models.dtos.responses.user;

import java.time.LocalDateTime;

public record UserLongResponse(
        Long id,
        String username,

        String firstName,
        String lastName,

        String email,
        String phoneNumber,
        String photoUrl,

        String role,

        Boolean isBlocked,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
