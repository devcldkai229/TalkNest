package com.backend.TalkNestResourceServer.repository;

import com.backend.TalkNestResourceServer.domain.dtos.conversations.ConversationWithLastMessageResult;
import com.backend.TalkNestResourceServer.domain.entities.ConversationUser;
import com.backend.TalkNestResourceServer.domain.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConversationUserRepository extends JpaRepository<ConversationUser, UUID> {

    @Query("SELECT c FROM ConversationUser c WHERE " +
            "(c.thisUser = :user1 AND c.otherUser = :user2) OR " +
            "(c.thisUser = :user2 AND c.otherUser = :user1)")
    Optional<ConversationUser> findConversationBetweenUsers(
            @Param("user1") Users user1,
            @Param("user2") Users user2
    );

    @Query("SELECT c FROM ConversationUser c WHERE c.thisUser = :user OR c.otherUser = :user " +
            "ORDER BY c.createdAt DESC")
    Page<ConversationUser> findConversationsByUser(@Param("user") Users user, Pageable pageable);

    @Query("SELECT new com.backend.TalkNestResourceServer.domain.dtos.conversations.ConversationWithLastMessageResult(" +
            "c.id, " +                                    // conversationId
            "c.thisUser.id, " +                           // thisUserId
            "CONCAT(c.thisUser.profile.firstName, ' ', c.thisUser.profile.lastName), " +  // thisFullName
            "c.otherUser.id, " +
            "CONCAT(c.otherUser.profile.firstName, ' ', c.otherUser.profile.lastName), " +
            "c.otherUser.profile.avatarUrl, " +
            "c.createdAt, " +
            "m.id, m.sender.id, m.sender.username, " +
            "m.content, m.messageType, m.isRead, m.createdAt" +
            ") " +
            "FROM ConversationUser c " +
            "JOIN MessagesUser m ON m.conversation = c " +
            "WHERE (c.thisUser = :user OR c.otherUser = :user) " +
            "AND m.createdAt = (" +
            "   SELECT MAX(m2.createdAt) FROM MessagesUser m2 WHERE m2.conversation = c" +
            ") " +
            "ORDER BY m.createdAt DESC")
    List<ConversationWithLastMessageResult> findConversationsWithLastMessage(@Param("user") Users user);


}
