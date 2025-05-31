package com.backend.TalkNestResourceServer.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    @Id
    private String id;

    private String token;

    private LocalDateTime expiryAt;

}
