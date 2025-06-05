package com.backend.TalkNestResourceServer.service.impl;

import com.backend.TalkNestResourceServer.domain.dtos.users.profile.ChangeProfileAvatarRequest;
import com.backend.TalkNestResourceServer.domain.dtos.users.profile.ProfileDetailsResponse;
import com.backend.TalkNestResourceServer.domain.dtos.users.profile.UpdateProfileRequest;
import com.backend.TalkNestResourceServer.domain.enums.Gender;
import com.backend.TalkNestResourceServer.exception.signature.UserNotExistsException;
import com.backend.TalkNestResourceServer.mapper.UserProfileMapper;
import com.backend.TalkNestResourceServer.repository.UserProfileRepository;
import com.backend.TalkNestResourceServer.service.CloudinaryService;
import com.backend.TalkNestResourceServer.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserProfileRepository userProfileRepository;

    private final CloudinaryService cloudinaryService;

    @Override
    public ProfileDetailsResponse getUserProfile(UUID userId) {
        var loadedUserProfile = userProfileRepository.findById(userId).orElseThrow(
                () -> new UserNotExistsException("User not found with id: " + userId.toString())
        );

        return UserProfileMapper.mapToProfileDetailsResponse(loadedUserProfile);
    }

    @Override
    public void updateUserProfile(UUID userId, UpdateProfileRequest request) {
        var loadedProfile = userProfileRepository.findById(userId).orElseThrow(
                () -> new UserNotExistsException("User not found with id: " + userId.toString())
        );

        loadedProfile.setAddress(request.getAddress());
        loadedProfile.setBio(request.getBio());
        loadedProfile.setGender(request.getGender().equals("Female") ? Gender.Female : Gender.Male);
        loadedProfile.setFirstName(request.getFirstName());
        loadedProfile.setLastName(request.getLastName());
        loadedProfile.setDayOfBirth(request.getDayOfBirth());

        userProfileRepository.save(loadedProfile);
    }

    @Override
    public ProfileDetailsResponse changeProfileAvatar(UUID userId, ChangeProfileAvatarRequest request) throws IOException {
        var loadedUser = userProfileRepository.findById(userId).orElseThrow(
                () -> new UserNotExistsException("User not found with id: " + userId.toString())
        );

        Map<String, Object> result = (Map<String, Object>) cloudinaryService.uploadImage(request.getFile(), request.getFolder(),
                loadedUser.getUserId().toString(), request.isOverwrite());

        loadedUser.setAvatarUrl(result.get("secure_url").toString());
        userProfileRepository.save(loadedUser);

        return UserProfileMapper.mapToProfileDetailsResponse(loadedUser);
    }
}
