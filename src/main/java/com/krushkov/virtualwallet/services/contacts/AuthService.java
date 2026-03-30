package com.krushkov.virtualwallet.services.contacts;

import com.krushkov.virtualwallet.models.User;
import com.krushkov.virtualwallet.models.dtos.requests.auth.LoginRequest;
import com.krushkov.virtualwallet.models.dtos.requests.auth.RegisterRequest;
import com.krushkov.virtualwallet.models.dtos.responses.auth.UserPrincipalResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    void register(User user);

    UserPrincipalResponse login(LoginRequest request, HttpServletResponse response);

    void logout(HttpServletResponse response);

    UserPrincipalResponse getMe();
}
