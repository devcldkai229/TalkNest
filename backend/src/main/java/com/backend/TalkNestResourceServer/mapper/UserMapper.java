package com.backend.TalkNestResourceServer.mapper;

import com.backend.TalkNestResourceServer.domain.dtos.users.RegisterUserRequest;
import com.backend.TalkNestResourceServer.domain.entities.Users;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserMapper {

    public static Users mapToUser(RegisterUserRequest registerUserRequest) {
        return Users.builder().username(registerUserRequest.getUsername())
                .password(registerUserRequest.getPassword())
                .email(registerUserRequest.getEmail()).build();
    }

}
