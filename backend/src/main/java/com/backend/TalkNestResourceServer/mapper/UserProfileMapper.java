package com.backend.TalkNestResourceServer.mapper;

import com.backend.TalkNestResourceServer.domain.dtos.users.UserProfileResponse;
import com.backend.TalkNestResourceServer.domain.dtos.users.profile.ProfileDetailsResponse;
import com.backend.TalkNestResourceServer.domain.entities.UserProfile;

import java.time.format.DateTimeFormatter;

public class UserProfileMapper {

    public static ProfileDetailsResponse mapToProfileDetailsResponse(UserProfile userProfile) {
        return ProfileDetailsResponse.builder()
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .bio(userProfile.getBio())
                .address(userProfile.getAddress())
                .gender(userProfile.getGender().toString())
                .phoneNumber(userProfile.getPhoneNumber())
                .avatarUrl(userProfile.getAvatarUrl())
                .dayOfBirth(userProfile.getDayOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .lastUpdated(userProfile.getLastUpdated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .build();
    }

    public static UserProfileResponse mapToUserResponse(UserProfile profile) {
        return UserProfileResponse.builder()
                .userId(profile.getUserId().toString())
                .bio(profile.getBio())
                .address(profile.getAddress())
                .gender(profile.getGender().toString())
                .lastName(profile.getLastName())
                .firstName(profile.getFirstName())
                .avatarUrl(profile.getAvatarUrl())
                .phoneNumber(profile.getPhoneNumber())
                .dayOfBirth(profile.getDayOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .build();
    }

}
