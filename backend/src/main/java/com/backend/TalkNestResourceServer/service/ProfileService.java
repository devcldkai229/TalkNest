package com.backend.TalkNestResourceServer.service;

import com.backend.TalkNestResourceServer.domain.dtos.users.profile.ChangeProfileAvatarRequest;
import com.backend.TalkNestResourceServer.domain.dtos.users.profile.ProfileDetailsResponse;
import com.backend.TalkNestResourceServer.domain.dtos.users.profile.UpdateProfileRequest;

import java.io.IOException;
import java.util.UUID;

public interface ProfileService {

    ProfileDetailsResponse getUserProfile(UUID userId);

    void updateUserProfile(UUID userId, UpdateProfileRequest request);

    ProfileDetailsResponse changeProfileAvatar(UUID userId, ChangeProfileAvatarRequest request) throws IOException;
}
