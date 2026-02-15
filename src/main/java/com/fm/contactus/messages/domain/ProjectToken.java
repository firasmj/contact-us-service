package com.fm.contactus.messages.domain;

import java.time.LocalDateTime;

public class ProjectToken {
    private Long id;
    private Long projectId;
    private String tokenEncoded;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Status status;

    public static enum Status {
        ACTIVE, INACTIVE, EXPIRED
    }

    public boolean isValid(LocalDateTime now) {
        return status == Status.ACTIVE && now.isBefore(expiresAt);
    }

    public static ProjectToken issue(Long projectId, String tokenEncoded, LocalDateTime expiresAt) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID is required");
        }
        if (tokenEncoded == null || tokenEncoded.isBlank()) {
            throw new IllegalArgumentException("Token value is required");
        }
        if (expiresAt == null) {
            throw new IllegalArgumentException("Token expiry is required");
        }

        return new ProjectToken(null, projectId, tokenEncoded, null, expiresAt, Status.ACTIVE);
    }

    public ProjectToken withStatus(Status newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Status is required");
        }
        return new ProjectToken(id, projectId, tokenEncoded, createdAt, expiresAt, newStatus);
    }

    public ProjectToken() {
    }

    public ProjectToken(Long id, Long projectId, String tokenEncoded, LocalDateTime createdAt, LocalDateTime expiresAt,
            Status status) {
        this.id = id;
        this.projectId = projectId;
        this.tokenEncoded = tokenEncoded;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.status = status;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getTokenEncoded() {
        return tokenEncoded;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public Status getStatus() {
        return status;
    }

}
