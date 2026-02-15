package com.fm.contactus.messages.infra.persistence;

import com.fm.contactus.messages.domain.ProjectToken;
import com.fm.contactus.messages.domain.ProjectToken.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "token")
public class TokenJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "token_encoded", nullable = false, unique = true)
    private String tokenEncoded;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private java.time.LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private java.time.LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjectToken.Status status;

    protected TokenJpaEntity() {
    }

    public Long getId() {
        return id;
    }

    public Long getProjectId() {
        return projectId;
    }

    void setStatus(Status status) {
        this.status = status;
    }

    public String getTokenEncoded() {
        return tokenEncoded;
    }

    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public java.time.LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public ProjectToken.Status getStatus() {
        return status;
    }

    void setExpiresAt(java.time.LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    void setTokenEncoded(String tokenEncoded) {
        this.tokenEncoded = tokenEncoded;
    }

    void setId(Long id) {
        this.id = id;
    }

    void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
