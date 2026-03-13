package com.krushkov.virtualwallet.models.dtos.responses.api;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiResponse<T>(
        boolean success,
        String path,
        String message,
        T data,
        Map<String, String> errors,
        LocalDateTime timestamp
) {
    public static <T> ApiResponse<T> success(
            String message,
            T data
    ) {
        return new ApiResponse<>(
                true,
                null,
                message,
                data,
                null,
                LocalDateTime.now()
        );
    }

    public static ApiResponse<Void> error(
            String path,
            String message,
            Map<String, String> errors
    ) {
        return new ApiResponse<>(
                false,
                path,
                message,
                null,
                errors,
                LocalDateTime.now()
        );
    }
}
