package com.backend.TalkNestResourceServer.domain.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Notification")
@Data
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private NotificationType typeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Users senderId;

    @Column(name = "entity_id", length = 100)
    private String entityId;

    @Column(name = "entity_type", length = 50)
    private String entityType;

    @Column(name = "additional_data", columnDefinition = "TEXT")
    private String additionalData;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_delivered", nullable = false)
    private Boolean isDelivered = false;

    @Column(name = "delivery_attempts", nullable = false)
    private Integer deliveryAttempts = 0;

    @Column(name = "is_read")
    private Boolean isRead = false;
}