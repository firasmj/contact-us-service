package com.fm.contactus.messages.application.port;

import java.util.Optional;

import com.fm.contactus.messages.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageRepositoryPort {
    Message save(Message message);
    Optional<Message> findById(Long id);
    Page<Message> findAll(Pageable pageable);
}
