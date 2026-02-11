package com.fm.contactus.messages.publicapi;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateMessageRequest(
    @NotNull Long projectId,
    @NotBlank @Size(max = 5000) String message,
    @Size(max = 255) String subject,
    @NotBlank @Size(max = 100) String name,
    @NotBlank @Email @Size(max = 100) String email,
    @Size(max = 20) String phone,
    @Size(max = 50) String type
) {}
