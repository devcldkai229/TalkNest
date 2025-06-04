package com.backend.TalkNestResourceServer.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "blocked")
public class Blocked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "blocker_id", nullable = false)
    private UUID blockerId;

    @Column(name = "blocked_id", nullable = false)
    private UUID blockedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocker_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Users blocker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Users blocked;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
