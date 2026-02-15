package com.fm.contactus.messages.adminapi;

import jakarta.validation.constraints.NotBlank;

public record RevokeTokenRequest(
    @NotBlank String tokenEncoded
) {
}
