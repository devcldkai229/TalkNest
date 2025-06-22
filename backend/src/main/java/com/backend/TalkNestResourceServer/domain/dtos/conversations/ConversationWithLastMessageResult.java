package com.backend.TalkNestResourceServer.domain.dtos.conversations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationWithLastMessageResult {

    private UUID conversationId;
    private UUID thisUserId;
    private String thisFullName;

    private UUID otherUserId;
    private String otherFullName;
    private String otherUserAvatar;

    private LocalDateTime conversationCreatedAt;

    private Long messageId;
    private UUID senderId;
    private String senderUsername;
    private String content;
    private String messageType;
    private Boolean isRead;
    private LocalDateTime messageCreatedAt;
}