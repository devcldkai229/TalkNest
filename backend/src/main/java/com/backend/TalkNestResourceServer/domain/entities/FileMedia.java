package com.backend.TalkNestResourceServer.domain.entities;

import com.backend.TalkNestResourceServer.domain.enums.FileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "file_media")
@NoArgsConstructor
@AllArgsConstructor
public class FileMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "content_type", length = 50)
    private String contentType = "MESSAGE";

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", length = 50)
    private FileType fileType;

    @Column(name = "file_name")
    private String fileName;

    @Column(length = 50)
    private String format;

    @Column(name = "cloudinary_public_id", length = 500, unique = true)
    private String cloudinaryPublicId;

    @Column(name = "cloudinary_url", length = 500)
    private String cloudinaryUrl;

    @Column(name = "cloudinary_secure_url", length = 500)
    private String cloudinarySecureUrl;

    private Integer width;

    private Integer height;

    private Integer duration;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
