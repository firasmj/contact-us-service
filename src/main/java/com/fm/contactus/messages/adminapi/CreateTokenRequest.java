package com.fm.contactus.messages.adminapi;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateTokenRequest(
    @NotNull Long projectId,
    @Min(1) Integer expiresInDays
) {
}
