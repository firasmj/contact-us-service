package com.fm.contactus.messages.publicapi;

import com.fm.contactus.messages.domain.Message;

import java.util.List;

public class MessagePublicMapper {

    public static GetMessagesResponse toGetMessagesResponse(List<Message> messages) {
        List<GetMessagesResponse.MessageItem> items = messages
            .stream()
            .map(MessagePublicMapper::toMessageItem)
            .toList();

        return new GetMessagesResponse(items);
    }

    private static GetMessagesResponse.MessageItem toMessageItem(Message message) {
        return new GetMessagesResponse.MessageItem(
            message.getId(),
            message.getProjectId(),
            message.getName(),
            message.getEmail(),
            message.getSubject(),
            message.getMessage(),
            message.getPhone(),
            message.getType(),
            message.getStatus().name(),
            message.getCreatedAt(),
            message.getUpdatedAt()
        );
    }
}