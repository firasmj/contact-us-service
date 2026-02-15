package com.fm.contactus.messages.application.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fm.contactus.messages.application.exception.InvalidTokenException;
import com.fm.contactus.messages.application.port.TokenRepositoryPort;
import com.fm.contactus.messages.domain.ProjectToken;
import com.fm.contactus.messages.domain.ProjectToken.Status;
import com.fm.contactus.messages.infra.persistence.ProjectJpaRepository;

import jakarta.transaction.Transactional;

@Service
public class TokenLifecycleService {

    private static final int TOKEN_BYTES = 32;
    private static final int MAX_TOKEN_GENERATION_ATTEMPTS = 10;
    private static final Base64.Encoder TOKEN_ENCODER = Base64.getUrlEncoder().withoutPadding();

    private final TokenRepositoryPort tokenRepository;
    private final ProjectJpaRepository projectRepository;
    private final SecureRandom secureRandom;
    private final int defaultExpiryDays;

    public TokenLifecycleService(
        TokenRepositoryPort tokenRepository,
        ProjectJpaRepository projectRepository,
        @Value("${contactus.tokens.default-expiry-days:365}") int defaultExpiryDays
    ) {
        if (defaultExpiryDays <= 0) {
            throw new IllegalArgumentException("contactus.tokens.default-expiry-days must be greater than zero");
        }
        this.tokenRepository = tokenRepository;
        this.projectRepository = projectRepository;
        this.defaultExpiryDays = defaultExpiryDays;
        this.secureRandom = new SecureRandom();
    }

    @Transactional
    public ProjectToken createToken(Long projectId, Integer expiresInDays) {
        validateProject(projectId);

        int expiryDays = resolveExpiryDays(expiresInDays);
        String tokenValue = generateUniqueToken();
        LocalDateTime expiresAt = LocalDateTime.now().plusDays(expiryDays);

        ProjectToken token = ProjectToken.issue(projectId, tokenValue, expiresAt);
        return tokenRepository.save(token);
    }

    @Transactional
    public ProjectToken revokeToken(String tokenEncoded) {
        ProjectToken token = tokenRepository.findTokenByEncodedValue(normalizeToken(tokenEncoded))
            .orElseThrow(() -> new InvalidTokenException("Token not found"));

        if (token.getStatus() == Status.INACTIVE) {
            return token;
        }

        return tokenRepository.save(token.withStatus(Status.INACTIVE));
    }

    @Transactional
    public ProjectToken rotateToken(String tokenEncoded, Integer expiresInDays) {
        ProjectToken currentToken = tokenRepository.findTokenByEncodedValue(normalizeToken(tokenEncoded))
            .orElseThrow(() -> new InvalidTokenException("Token not found"));

        tokenRepository.save(currentToken.withStatus(Status.INACTIVE));
        return createToken(currentToken.getProjectId(), expiresInDays);
    }

    private void validateProject(Long projectId) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID is required");
        }
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project does not exist");
        }
    }

    private int resolveExpiryDays(Integer expiresInDays) {
        int value = expiresInDays == null ? defaultExpiryDays : expiresInDays;
        if (value <= 0) {
            throw new IllegalArgumentException("Expiry days must be greater than zero");
        }
        return value;
    }

    private String generateUniqueToken() {
        for (int i = 0; i < MAX_TOKEN_GENERATION_ATTEMPTS; i++) {
            String tokenValue = generateTokenValue();
            if (!tokenRepository.existsByEncodedValue(tokenValue)) {
                return tokenValue;
            }
        }

        throw new IllegalStateException("Failed to generate a unique token");
    }

    private String generateTokenValue() {
        byte[] buffer = new byte[TOKEN_BYTES];
        secureRandom.nextBytes(buffer);
        return TOKEN_ENCODER.encodeToString(buffer);
    }

    private String normalizeToken(String tokenEncoded) {
        if (tokenEncoded == null || tokenEncoded.isBlank()) {
            throw new InvalidTokenException("Token value is required");
        }
        return tokenEncoded.trim();
    }
}
