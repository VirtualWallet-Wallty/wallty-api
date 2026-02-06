package com.krushkov.virtualwallet.models.dtos.requests.user;

import com.krushkov.virtualwallet.helpers.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(

        @Size(min = 4, max = 32, message = ValidationMessages.USERNAME_LENGTH_ERROR)
        String username,

        @NotBlank(message = ValidationMessages.PASSWORD_NOT_NULL_ERROR)
        @Size(min = 6, max = 128, message = ValidationMessages.PASSWORD_LENGTH_ERROR)
        String password,

        @Size(min = 4, max = 50, message = ValidationMessages.FIRST_NAME_LENGTH_ERROR)
        String firstName,

        @Size(min = 4, max = 50, message = ValidationMessages.LAST_NAME_LENGTH_ERROR)
        String lastName,

        @Email(message = ValidationMessages.EMAIL_INVALID_ERROR)
        @Size(min = 6, max = 255, message = ValidationMessages.EMAIL_LENGTH_ERROR)
        String email,

        @Size(min = 10, max = 10, message = ValidationMessages.PHONE_NUMBER_LENGTH_ERROR)
        String phoneNumber,

        @Size(min = 6, max = 512, message = ValidationMessages.PHOTO_URL_LENGTH_ERROR)
        String photoUrl

) {}
