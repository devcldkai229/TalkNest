package com.backend.TalkNestResourceServer.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "User_Profile")
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id
    private UUID userId;

    private String bio;

    private String address;

    private LocalDate dayOfBirth;

    private String phoneNumber;

    private String gender;

    private String avatarUrl;

    private LocalDateTime lastUpdated;
}
