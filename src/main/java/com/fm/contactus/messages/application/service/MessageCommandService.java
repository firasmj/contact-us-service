package com.fm.contactus.messages.application.service;

import org.springframework.stereotype.Service;

import com.fm.contactus.messages.application.port.MessageRepositoryPort;
import com.fm.contactus.messages.domain.Message;

import jakarta.transaction.Transactional;

@Service
public class MessageCommandService {

    private final MessageRepositoryPort messageRepository;

    public MessageCommandService(MessageRepositoryPort messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public Long createMessage(Long projectId, String name, String email, String message, String subject, String phone, String type) {
        Message msg = Message.createNew(projectId, message, subject, name, email, phone, type);
        Message savedMessage = messageRepository.save(msg);
        return savedMessage.getId();
    }

}
