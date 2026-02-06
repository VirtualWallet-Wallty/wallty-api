package com.krushkov.virtualwallet.models.dtos.requests.auth;

import com.krushkov.virtualwallet.helpers.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = ValidationMessages.USERNAME_NOT_NULL_ERROR)
        @Size(min = 4, max = 32, message = ValidationMessages.USERNAME_LENGTH_ERROR)
        String username,

        @NotBlank(message = ValidationMessages.PASSWORD_NOT_NULL_ERROR)
        @Size(min = 6, max = 128, message = ValidationMessages.PASSWORD_LENGTH_ERROR)
        String password,

        @NotBlank(message = ValidationMessages.FIRST_NAME_NOT_NULL_ERROR)
        @Size(min = 4, max = 50, message = ValidationMessages.FIRST_NAME_LENGTH_ERROR)
        String firstName,

        @NotBlank(message = ValidationMessages.LAST_NAME_NOT_NULL_ERROR)
        @Size(min = 4, max = 50, message = ValidationMessages.LAST_NAME_LENGTH_ERROR)
        String lastName,

        @NotBlank(message = ValidationMessages.EMAIL_NOT_NULL_ERROR)
        @Email(message = ValidationMessages.EMAIL_INVALID_ERROR)
        @Size(min = 6, max = 255, message = ValidationMessages.EMAIL_LENGTH_ERROR)
        String email
) {}
