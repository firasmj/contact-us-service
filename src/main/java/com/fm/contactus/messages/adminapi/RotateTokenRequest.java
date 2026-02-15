package com.fm.contactus.messages.adminapi;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RotateTokenRequest(
    @NotBlank String tokenEncoded,
    @Min(1) Integer expiresInDays
) {
}
