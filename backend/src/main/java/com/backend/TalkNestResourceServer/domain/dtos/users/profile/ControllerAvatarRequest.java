package com.backend.TalkNestResourceServer.domain.dtos.users.profile;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ControllerAvatarRequest {
    @NotNull
    private MultipartFile file;
}
