package com.backend.TalkNestResourceServer.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "File_Media")
@NoArgsConstructor
@AllArgsConstructor
public class FileMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long contentId;

    private String contentType;

    private String filetype;

    private String fileName;

    private Long fileSize;

    private String cloudinaryPublicId;

    private String cloudinaryUrl;

    private String cloudinarySecureUrl;

    private Integer width;

    private Integer height;

    private Integer duration;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
