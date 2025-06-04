package com.backend.TalkNestResourceServer.repository;

import com.backend.TalkNestResourceServer.domain.entities.Blocked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockedRepository extends JpaRepository<Blocked, Long> {

    List<Blocked> getBlockedByBlockerId(String blockerId);

}