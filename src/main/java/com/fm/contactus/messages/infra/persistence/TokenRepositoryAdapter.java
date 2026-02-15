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
        return tokenJpaRepository.findByTokenEncoded(encodedValue)
            .map(TokenPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByEncodedValue(String encodedValue) {
        return tokenJpaRepository.existsByTokenEncoded(encodedValue);
    }

    @Override
    public ProjectToken save(ProjectToken token) {
        TokenJpaEntity savedEntity = tokenJpaRepository.save(TokenPersistenceMapper.toEntity(token));
        return TokenPersistenceMapper.toDomain(savedEntity);
    }
}
