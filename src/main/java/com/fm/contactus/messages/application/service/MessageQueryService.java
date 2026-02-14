package com.fm.contactus.messages.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fm.contactus.messages.application.port.MessageRepositoryPort;
import com.fm.contactus.messages.domain.Message;

@Service
public class MessageQueryService {
    
    public final MessageRepositoryPort messageRepository;

    public MessageQueryService(MessageRepositoryPort messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessagesByProject(Long projectId) {
        return messageRepository.findByProjectId(projectId);
    }

}
