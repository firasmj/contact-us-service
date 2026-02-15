package com.fm.contactus.messages.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fm.contactus.messages.application.exception.MissingTokenException;
import com.fm.contactus.messages.application.port.TokenRepositoryPort;

@ExtendWith(MockitoExtension.class)
public class TokenValidationServiceTest {
    
    @Mock TokenRepositoryPort tokenRepository;
    TokenValidationService service;

    @BeforeEach void setUp() {
        service = new TokenValidationService(tokenRepository);
    }
    
    @Test
    void resolveActiveToken_null_throwsMissingToken() {
        assertThrows(MissingTokenException.class, () -> service.resolveActiveToken(null));
        verifyNoInteractions(tokenRepository);
    }
}
