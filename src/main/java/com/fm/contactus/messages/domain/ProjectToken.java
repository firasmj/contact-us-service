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
