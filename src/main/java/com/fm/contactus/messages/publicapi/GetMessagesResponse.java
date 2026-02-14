package com.fm.contactus.messages.publicapi;

import java.time.LocalDateTime;
import java.util.List;

public record GetMessagesResponse(List<MessageItem> messages) {
	public record MessageItem(
		Long id,
		Long projectId,
		String name,
		String email,
		String subject,
		String message,
		String phone,
		String type,
		String status,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
	) {
	}
}
