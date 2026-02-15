package com.fm.contactus.messages.adminapi;

import java.time.LocalDateTime;

public record TokenAdminResponse(
    Long id,
    Long projectId,
    String tokenEncoded,
    String status,
    LocalDateTime createdAt,
    LocalDateTime expiresAt
) {
}
