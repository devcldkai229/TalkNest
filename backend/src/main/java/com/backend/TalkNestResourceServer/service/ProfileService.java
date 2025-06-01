package com.backend.TalkNestResourceServer.service;

import com.backend.TalkNestResourceServer.domain.dtos.users.profile.ChangeProfileAvatarRequest;
import com.backend.TalkNestResourceServer.domain.dtos.users.profile.ProfileDetailsResponse;
import com.backend.TalkNestResourceServer.domain.dtos.users.profile.UpdateProfileRequest;

import java.util.UUID;

public interface ProfileService {

    ProfileDetailsResponse getUserProfile(UUID userId);

    ProfileDetailsResponse updateUserProfile(UUID userId, UpdateProfileRequest request);

    void changeProfileAvatar(UUID userId, ChangeProfileAvatarRequest request);
}
