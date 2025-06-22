package com.backend.TalkNestResourceServer.domain.dtos.groups;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeAvatarRequest {
    private String groupId;

    private MultipartFile file;

    private String folder;

    private boolean overwrite;
}
