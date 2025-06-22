package com.backend.TalkNestResourceServer.domain.entities;

import com.backend.TalkNestResourceServer.domain.enums.PrivacyLevel;
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
@Table(name = "Groups")
public class Group {

    @Id
    private UUID id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private PrivacyLevel privacyLevel = PrivacyLevel.Private;

    private String avatarGroupUrl;

    @Column(name = "creator_Id", nullable = false)
    private UUID creatorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_Id", referencedColumnName = "id", insertable = false, nullable = false)
    private Users creator;

    private boolean isDeleted = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
