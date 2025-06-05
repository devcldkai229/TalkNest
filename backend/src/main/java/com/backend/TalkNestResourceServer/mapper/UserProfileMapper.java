package com.backend.TalkNestResourceServer.mapper;

import com.backend.TalkNestResourceServer.domain.dtos.users.UserProfileResponse;
import com.backend.TalkNestResourceServer.domain.dtos.users.profile.ProfileDetailsResponse;
import com.backend.TalkNestResourceServer.domain.entities.UserProfile;

import java.time.format.DateTimeFormatter;

public class UserProfileMapper {

    public static ProfileDetailsResponse mapToProfileDetailsResponse(UserProfile userProfile) {
        var lastUpdated = userProfile.getLastUpdated();
        var dayOfBirth = userProfile.getDayOfBirth();
        return ProfileDetailsResponse.builder()
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .bio(userProfile.getBio())
                .address(userProfile.getAddress())
                .gender(userProfile.getGender().toString())
                .phoneNumber(userProfile.getPhoneNumber())
                .avatarUrl(userProfile.getAvatarUrl())
                .dayOfBirth(dayOfBirth!=null?dayOfBirth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")):"#NoData")
                .lastUpdated(lastUpdated!=null?lastUpdated.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")):"#NoData")
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
