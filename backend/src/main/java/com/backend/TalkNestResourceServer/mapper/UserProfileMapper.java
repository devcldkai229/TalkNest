package com.backend.TalkNestResourceServer.mapper;

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

}
