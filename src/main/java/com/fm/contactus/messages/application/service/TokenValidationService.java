package com.fm.contactus.messages.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.fm.contactus.messages.application.exception.InvalidTokenException;
import com.fm.contactus.messages.application.exception.MissingTokenException;
import com.fm.contactus.messages.application.port.TokenRepositoryPort;
import com.fm.contactus.messages.domain.ProjectToken;

@Service
public class TokenValidationService {

    private final TokenRepositoryPort tokenRepository;

    public TokenValidationService(TokenRepositoryPort tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public ProjectToken resolveActiveToken(String encodedToken) {
        if (encodedToken == null || encodedToken.isBlank()) {
            throw new MissingTokenException("Invalid token: Token is missing from the request");
        }

        ProjectToken token = tokenRepository.findTokenByEncodedValue(encodedToken)
                .orElseThrow(() -> new InvalidTokenException("Provided token is invalid or does not exist"));

        if (!token.isValid(LocalDateTime.now())) {
            throw new InvalidTokenException("Provided token is expired or inactive");
        }
        return token;
    }
}
