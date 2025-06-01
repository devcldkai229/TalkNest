package com.backend.TalkNestResourceServer.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Follower")
@Builder
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private User follower;

    private User followed;

}
