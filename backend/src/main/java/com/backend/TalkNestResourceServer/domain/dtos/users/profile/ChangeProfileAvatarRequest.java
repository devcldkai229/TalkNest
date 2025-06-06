package com.backend.TalkNestResourceServer.domain.dtos.users.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeProfileAvatarRequest {

    private MultipartFile file;

    private String folder;

    private boolean overwrite;

}
