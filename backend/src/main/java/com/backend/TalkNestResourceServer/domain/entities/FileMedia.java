package com.backend.TalkNestResourceServer.domain.entities;

import com.backend.TalkNestResourceServer.domain.enums.ContentType;
import com.backend.TalkNestResourceServer.domain.enums.FileType;
import com.backend.TalkNestResourceServer.domain.enums.FormatFile;
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

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @Enumerated(EnumType.STRING)
    private FileType filetype;

    private String fileName;

    private String cloudinaryPublicId;

    private String cloudinaryUrl;

    private String cloudinarySecureUrl;

    @Enumerated(EnumType.STRING)
    private FormatFile format;

    private Integer width;

    private Integer height;

    private Integer duration;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
