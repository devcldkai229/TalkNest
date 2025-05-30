package com.backend.TalkNestResourceServer.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "Verification_Token")
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {

    @Id
    private String token;

    private UUID userId;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    public boolean isExpired() {
        return expiredAt != null && expiredAt.isBefore(LocalDateTime.now());
    }
}
