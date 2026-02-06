package com.krushkov.virtualwallet.controllers;

import com.krushkov.virtualwallet.helpers.mappers.UserMapper;
import com.krushkov.virtualwallet.models.User;
import com.krushkov.virtualwallet.models.dtos.requests.auth.LoginRequest;
import com.krushkov.virtualwallet.models.dtos.requests.auth.RegisterRequest;
import com.krushkov.virtualwallet.models.dtos.responses.auth.UserPrincipalResponse;
import com.krushkov.virtualwallet.services.contacts.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest request) {
        User user = userMapper.fromRegister(request);
        authService.register(user);
    }

    @PostMapping("/login")
    public UserPrincipalResponse login(
            @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        return authService.login(request, response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        authService.logout(response);
    }
}
