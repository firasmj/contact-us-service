package com.fm.contactus.messages.adminapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fm.contactus.messages.application.service.MessageQueryService;
import com.fm.contactus.messages.publicapi.GetMessagesResponse;
import com.fm.contactus.messages.publicapi.MessagePublicMapper;

@RestController
@RequestMapping("/api/admin/messages")
public class MessageAdminController {

    private final MessageQueryService messageQueryService;

    public MessageAdminController(MessageQueryService messageQueryService) {
        this.messageQueryService = messageQueryService;
    }

    @GetMapping
    public GetMessagesResponse getMessagesByProject(@RequestParam Long projectId) {
        return MessagePublicMapper.toGetMessagesResponse(
            messageQueryService.getMessagesByProject(projectId)
        );
    }
}
