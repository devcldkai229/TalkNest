package com.backend.TalkNestResourceServer.domain.entities;

import com.backend.TalkNestResourceServer.domain.enums.Gender;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String avatarUrl;

    private LocalDateTime lastUpdated;
}
