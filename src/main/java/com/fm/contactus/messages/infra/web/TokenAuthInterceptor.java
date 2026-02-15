package com.fm.contactus.messages.infra.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fm.contactus.messages.application.service.TokenValidationService;
import com.fm.contactus.messages.domain.ProjectToken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenAuthInterceptor implements HandlerInterceptor {
    
    public static final String TOKEN_AUTH_CONTEXT_KEY = "AUTH_CONTEXT";

    private final TokenValidationService tokenValidationService;

    public TokenAuthInterceptor(TokenValidationService tokenValidationService) {
        this.tokenValidationService = tokenValidationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tokenHeader = request.getHeader("X-Project-Token");
        ProjectToken token = tokenValidationService.resolveActiveToken(tokenHeader);
        TokenAuthContext authContext = new TokenAuthContext(token.getId(), token.getProjectId(), token.getTokenEncoded());
        request.setAttribute(TOKEN_AUTH_CONTEXT_KEY, authContext);
        return true;
    }
}
