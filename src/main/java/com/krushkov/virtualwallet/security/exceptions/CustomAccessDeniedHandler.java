package com.krushkov.virtualwallet.security.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krushkov.virtualwallet.helpers.ValidationMessages;
import com.krushkov.virtualwallet.models.dtos.responses.api.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        ApiErrorResponse errorResponse =
                ApiErrorResponse.error(
                        HttpStatus.FORBIDDEN.value(),
                        request.getRequestURI(),
                        ValidationMessages.API_ACCESS_ERROR
                );

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
