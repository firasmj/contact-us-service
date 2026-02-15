package com.fm.contactus.messages.infra.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.fm.contactus.messages.application.port.TokenRepositoryPort;
import com.fm.contactus.messages.domain.ProjectToken;

@Repository
public class TokenRepositoryAdapter implements TokenRepositoryPort {
    
    private final TokenJpaRepository tokenJpaRepository;

    public TokenRepositoryAdapter(TokenJpaRepository tokenJpaRepository) {
        this.tokenJpaRepository = tokenJpaRepository;
    }

    @Override
    public Optional<ProjectToken> findTokenByEncodedValue(String encodedValue) {
        Optional<TokenJpaEntity> tokenEntity = tokenJpaRepository.findByTokenEncoded(encodedValue);
        if (tokenEntity.isPresent()) {
            return Optional.of(TokenPersistenceMapper.toDomain(tokenEntity.get()));
        } else {
            return Optional.empty();
        }
    }
}
