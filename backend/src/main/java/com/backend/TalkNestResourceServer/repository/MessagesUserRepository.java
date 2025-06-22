package com.backend.TalkNestResourceServer.repository;

import com.backend.TalkNestResourceServer.domain.entities.ConversationUser;
import com.backend.TalkNestResourceServer.domain.entities.MessagesUser;
import com.backend.TalkNestResourceServer.domain.entities.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesUserRepository extends JpaRepository<MessagesUser, Long> {

    Page<MessagesUser> findByConversationOrderByCreatedAtDesc(ConversationUser conversation, Pageable pageable);

    @Query("SELECT COUNT(m) FROM MessagesUser m WHERE m.receiver :receiver AND m.isRead = false ")
    Long countUnreadMessagesByReceiver(@Param("receiver") Users receiver);

    @Query("SELECT COUNT(m) FROM MessagesUser m WHERE m.receiver : receiver AND m.conversation :conversation AND m.isRead = false")
    Long countUnreadMessagesInConversation(@Param("conversation") ConversationUser conversation, @Param("receiver") Users user);

    @Modifying
    @Transactional
    @Query("UPDATE MessagesUser m SET isRead = true WHERE m.conversation :conversation AND m.receiver :receiver AND m.isRead = false")
    int markMessagesAsRead(@Param("conversation") ConversationUser conversation, @Param("receiver") Users receiver);

    @Query("SELECT m FROM MessagesUser m WHERE m.conversation :conversation ORDER BY m.createdAt DESC LIMIT 1")
    MessagesUser findLatestMessageInConversation(@Param("conversation") ConversationUser conversation);
}
