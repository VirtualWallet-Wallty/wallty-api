package com.krushkov.virtualwallet.models.dtos.responses.user;

public record UserShortResponse(
        Long id,
        String username,

        String firstName,
        String lastName,

        String photoUrl
) {}
