package com.backend.TalkNestResourceServer.repository;

import com.backend.TalkNestResourceServer.domain.entities.Blocked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlockedRepository extends JpaRepository<Blocked, Long> {

    List<Blocked> findByBlockerId(UUID blockerId);

    Optional<Blocked> findByBlockerIdAndBlockedId(UUID blockerId, UUID blockedId);

    boolean isExistByBlockerIdAndBlockedId(UUID blockerId, UUID blockedId);
}