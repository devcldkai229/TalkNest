package com.backend.TalkNestResourceServer.service.impl;

import com.backend.TalkNestResourceServer.domain.dtos.users.profile.ChangeProfileAvatarRequest;
import com.backend.TalkNestResourceServer.domain.dtos.users.profile.ProfileDetailsResponse;
import com.backend.TalkNestResourceServer.domain.dtos.users.profile.UpdateProfileRequest;
import com.backend.TalkNestResourceServer.repository.UserProfileRepository;
import com.backend.TalkNestResourceServer.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserProfileRepository userProfileRepository;

    @Override
    public ProfileDetailsResponse getUserProfile(UUID userId) {
        return null;
    }

    @Override
    public ProfileDetailsResponse updateUserProfile(UUID userId, UpdateProfileRequest request) {
        return null;
    }

    @Override
    public void changeProfileAvatar(UUID userId, ChangeProfileAvatarRequest request) {

    }
}
