package com.fm.contactus.messages.publicapi;

import com.fm.contactus.messages.application.service.MessageCommandService;
import com.fm.contactus.messages.application.service.MessageQueryService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/messages")
public class MessagePublicController {
    
    private final MessageCommandService messageCommandService;
    private final MessageQueryService messageQueryService;

    public MessagePublicController(
        MessageCommandService messageCommandService,
        MessageQueryService messageQueryService
    ) {
        this.messageCommandService = messageCommandService;
        this.messageQueryService = messageQueryService;
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

    @GetMapping
    public GetMessagesResponse getMessagesByProject(@RequestParam @NotNull Long projectId) {
        return MessagePublicMapper.toGetMessagesResponse(messageQueryService.getMessagesByProject(projectId));
    }
}
