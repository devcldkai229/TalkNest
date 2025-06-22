package com.backend.TalkNestResourceServer.domain.dtos.groups;

import com.backend.TalkNestResourceServer.domain.dtos.users.UserProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponse {

    private String id;

    private String name;

    private String description;

    private String privacyLevel;

    private String avatarGroupUrl;

    private UserProfileResponse userProfile;

    private String createdAt;

    private String updatedAt;

}
