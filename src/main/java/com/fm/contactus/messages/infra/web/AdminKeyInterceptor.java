package com.fm.contactus.messages.infra.web;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fm.contactus.messages.application.exception.AdminUnauthorizedException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AdminKeyInterceptor implements HandlerInterceptor {

    public static final String ADMIN_KEY_HEADER = "X-Admin-Key";

    private final String adminApiKey;

    public AdminKeyInterceptor(@Value("${contactus.admin.api-key:}") String adminApiKey) {
        this.adminApiKey = adminApiKey == null ? "" : adminApiKey.trim();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }

        String providedAdminKey = request.getHeader(ADMIN_KEY_HEADER);
        if (!isAuthorized(providedAdminKey)) {
            throw new AdminUnauthorizedException("Invalid admin credentials");
        }

        return true;
    }

    private boolean isAuthorized(String providedAdminKey) {
        if (adminApiKey.isBlank() || providedAdminKey == null || providedAdminKey.isBlank()) {
            return false;
        }

        byte[] expected = adminApiKey.getBytes(StandardCharsets.UTF_8);
        byte[] actual = providedAdminKey.trim().getBytes(StandardCharsets.UTF_8);
        return MessageDigest.isEqual(expected, actual);
    }
}
