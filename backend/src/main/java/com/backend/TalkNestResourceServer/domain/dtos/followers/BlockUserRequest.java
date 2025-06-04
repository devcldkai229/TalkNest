package com.backend.TalkNestResourceServer.domain.dtos.followers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlockUserRequest {

    private String blockerId;

    private String blockedId;

}
