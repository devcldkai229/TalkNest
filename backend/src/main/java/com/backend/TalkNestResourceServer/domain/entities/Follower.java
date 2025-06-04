package com.backend.TalkNestResourceServer.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

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

    @Column(name = "follower_id", nullable = false)
    private UUID followerId;

    @Column(name = "followed_id", nullable = false)
    private UUID followedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", referencedColumnName = "id", insertable = false, nullable = false)
    private Users follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_id", referencedColumnName = "id", insertable = false, nullable = false)
    private Users followed;

}
