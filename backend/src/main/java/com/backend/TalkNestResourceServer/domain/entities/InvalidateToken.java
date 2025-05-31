package com.backend.TalkNestResourceServer.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Invalidate_Token")
public class InvalidateToken {

    private String id;

    private String token;

    private LocalDateTime expiryAt;

}
