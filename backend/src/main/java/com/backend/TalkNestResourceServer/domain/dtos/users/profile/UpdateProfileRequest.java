package com.backend.TalkNestResourceServer.domain.dtos.users.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProfileRequest {

    private String firstName;

    private String lastName;

    private String bio;

    private String address;

    private LocalDate dayOfBirth;

    private String gender;

}
