package com.backend.TalkNestResourceServer.domain.dtos.users.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDetailsResponse {

    private String firstName;

    private String lastName;

    private String bio;

    private String address;

    private String dayOfBirth;

    private String phoneNumber;

    private String gender;

    private String avatarUrl;

    private String lastUpdated;


}
