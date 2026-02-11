package com.fm.contactus.messages.infra.persistence;

import com.fm.contactus.messages.domain.Message;

public class MessagePersistenceMapper {
    
    public static MessageJpaEntity toEntity(Message message) {
        MessageJpaEntity entity = new MessageJpaEntity();
        entity.setId(message.getId());
        entity.setProjectId(message.getProjectId());
        entity.setMessage(message.getMessage());
        entity.setSubject(message.getSubject());
        entity.setName(message.getName());
        entity.setEmail(message.getEmail());
        entity.setPhone(message.getPhone());
        entity.setType(message.getType());
        entity.setStatus(message.getStatus());
        entity.setIpAddress(message.getIpAddress());
        return entity;
    }

    public static Message toDomain(MessageJpaEntity entity) {
        Message.MessageStatus status = Message.MessageStatus.NEW;
        if (entity.getStatus() != null) {
            status = entity.getStatus();
        }

        return new Message(
            entity.getId(),
            entity.getProjectId(),
            entity.getMessage(),
            entity.getSubject(),
            entity.getName(),
            entity.getEmail(),
            entity.getPhone(),
            entity.getType(),
            status,
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getIpAddress()
        );
    }
    
}
