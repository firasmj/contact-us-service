package com.fm.contactus.messages.publicapi;

import com.fm.contactus.messages.application.service.MessageCommandService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/messages")
public class MessagePublicController {
    
    private final MessageCommandService messageCommandService;

    public MessagePublicController(MessageCommandService messageCommandService) {
        this.messageCommandService = messageCommandService;
    }

    @PostMapping
    public CreateMessageResponse createMessage(@Valid @RequestBody CreateMessageRequest request) {
        Long messageId = messageCommandService.createMessage(
            request.projectId(),
            request.name(),
            request.email(),
            request.message(),
            request.subject(),
            request.phone(),
            request.type()
        );

        return new CreateMessageResponse(messageId, "NEW");
    }
}
