package com.backend.TalkNestResourceServer.repository;

import com.backend.TalkNestResourceServer.domain.entities.FileMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMediaRepository extends JpaRepository<FileMedia, Long> {
}
