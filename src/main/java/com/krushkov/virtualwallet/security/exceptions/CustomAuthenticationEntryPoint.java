package com.krushkov.virtualwallet.security.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krushkov.virtualwallet.helpers.ValidationMessages;
import com.krushkov.virtualwallet.models.dtos.responses.api.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        ApiErrorResponse errorResponse = ApiErrorResponse.error(
                HttpStatus.UNAUTHORIZED.value(),
                request.getRequestURI(),
                ValidationMessages.AUTHENTICATION_MISSING_ERROR
        );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
