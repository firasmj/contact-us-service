package com.fm.contactus.messages.infra.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fm.contactus.messages.application.port.MessageRepositoryPort;
import com.fm.contactus.messages.domain.Message;

@Repository
public class MessageRepositoryAdapter implements MessageRepositoryPort {
    private final MessageJpaRepository messageJpaRepository;

    public MessageRepositoryAdapter(MessageJpaRepository messageJpaRepository) {
        this.messageJpaRepository = messageJpaRepository;
    }

    @Override
    public Message save(Message message) {
        MessageJpaEntity entity = MessagePersistenceMapper.toEntity(message);
        MessageJpaEntity savedEntity = messageJpaRepository.save(entity);
        return MessagePersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Message> findById(Long id) {
        return messageJpaRepository.findById(id)
            .map(MessagePersistenceMapper::toDomain);
    }

    @Override
    public Page<Message> findAll(Pageable pageable) {
        return messageJpaRepository.findAll(pageable)
            .map(MessagePersistenceMapper::toDomain);
    }
}
