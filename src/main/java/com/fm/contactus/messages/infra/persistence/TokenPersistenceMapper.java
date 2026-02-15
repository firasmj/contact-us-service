package com.fm.contactus.messages.infra.persistence;

import com.fm.contactus.messages.domain.ProjectToken;

public class TokenPersistenceMapper {
    
    public static ProjectToken toDomain(TokenJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new ProjectToken(
            entity.getId(),
            entity.getProjectId(),
            entity.getTokenEncoded(),
            entity.getCreatedAt(),
            entity.getExpiresAt(),
            entity.getStatus()
        );
    }

    public static TokenJpaEntity toEntity(ProjectToken token) {
        if (token == null) {
            return null;
        }
        TokenJpaEntity entity = new TokenJpaEntity();
        entity.setId(token.getId());
        entity.setProjectId(token.getProjectId());
        entity.setTokenEncoded(token.getTokenEncoded());
        entity.setCreatedAt(token.getCreatedAt());
        entity.setExpiresAt(token.getExpiresAt());
        entity.setStatus(token.getStatus());
        return entity;
    }
}
