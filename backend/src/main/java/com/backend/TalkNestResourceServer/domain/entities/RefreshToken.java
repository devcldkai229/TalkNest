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
@Entity
@Builder
@Table(name = "Refresh_Token")
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    private UUID userId;

    @Column(length = 1000)
    private String token;

    private LocalDateTime expiredDate;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
