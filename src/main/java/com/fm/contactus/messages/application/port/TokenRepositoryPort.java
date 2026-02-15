package com.fm.contactus.messages.application.port;

import java.util.Optional;

import com.fm.contactus.messages.domain.ProjectToken;

public interface TokenRepositoryPort {
    
    Optional<ProjectToken> findTokenByEncodedValue(String encodedValue);
}
