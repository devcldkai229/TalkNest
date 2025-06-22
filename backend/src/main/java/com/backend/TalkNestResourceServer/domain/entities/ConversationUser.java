package com.backend.TalkNestResourceServer.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Conversation_User")
@Data
@EqualsAndHashCode(exclude = "messages")
@ToString(exclude = "messages")
public class ConversationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "this_user")
    private Users thisUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "other_user")
    private Users otherUser;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MessagesUser> messages;
}