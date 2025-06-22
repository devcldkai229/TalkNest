package com.backend.TalkNestResourceServer.domain.dtos.groups;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateGroupRequest {

    private String name;

    private String description;

    private String privacyLevel;

    private String creatorId;

}
