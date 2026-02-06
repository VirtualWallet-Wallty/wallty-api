package com.krushkov.virtualwallet.security.jwt;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class JwtCookieUtil {

    public static final String COOKIE_NAME = "JWT_TOKEN";

    @Value("${security.jwt.expiration}")
    private long expiration;

    public void addTokenCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, token)
                .httpOnly(true)
                .secure(false)
                .sameSite("None")
                .path("/")
                .maxAge(expiration)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public void clearTokenCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, "")
                .httpOnly(true)
                .secure(false)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }
}
