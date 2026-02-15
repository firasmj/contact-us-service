package com.fm.contactus.messages.domain;

import java.time.LocalDateTime;

public class Message {
    private Long id;
    private Long projectId;
    private String message;
    private String subject;
    private String name;
    private String email;
    private String phone;
    private String type;
    private MessageStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String ipAddress;

    public Message() {
    }

    public Message(Long id, Long projectId, String message, String subject, String name, String email, String phone,
            String type, MessageStatus status, String ipAddress) {
        this(id, projectId, message, subject, name, email, phone, type, status, null, null, ipAddress);
    }

    public Message(Long id, Long projectId, String message, String subject, String name, String email, String phone,
            String type, MessageStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, String ipAddress) {
        this.id = id;
        this.projectId = projectId;
        this.message = message;
        this.subject = subject;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.ipAddress = ipAddress;
    }

    public Long getId() {
        return id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getMessage() {
        return message;
    }

    public String getSubject() {
        return subject;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void markAsRead() {
        if (status == MessageStatus.ARCHIVED) {
            throw new IllegalStateException("Archived message cannot be read");
        }
        this.status = MessageStatus.READ;
    }

    public enum MessageStatus {
        NEW,
        READ,
        ARCHIVED
    }

    public static Message createNew(Long projectId, String message, String subject, String name, String email, String phone, String type) {
        return createNew(projectId, message, subject, name, email, phone, type, null);
    }

    public static Message createNew(
        Long projectId,
        String message,
        String subject,
        String name,
        String email,
        String phone,
        String type,
        String ipAddress
    ) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message is required");
        }
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID is required");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        return new Message(null, projectId, message, subject, name, email, phone, type, MessageStatus.NEW, null, null, ipAddress);
    }

}
