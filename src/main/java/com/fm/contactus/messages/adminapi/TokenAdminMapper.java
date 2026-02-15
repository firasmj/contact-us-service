package com.fm.contactus.messages.adminapi;

import com.fm.contactus.messages.domain.ProjectToken;

public class TokenAdminMapper {

    private TokenAdminMapper() {
    }

    public static TokenAdminResponse toResponse(ProjectToken token) {
        return new TokenAdminResponse(
            token.getId(),
            token.getProjectId(),
            token.getTokenEncoded(),
            token.getStatus().name(),
            token.getCreatedAt(),
            token.getExpiresAt()
        );
    }
}
