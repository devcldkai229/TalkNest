package com.backend.TalkNestResourceServer.mapper;

import com.backend.TalkNestResourceServer.domain.dtos.users.UserRegisterDTO;
import com.backend.TalkNestResourceServer.domain.entities.Users;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserMapper {

    public static Users mapToUser(UserRegisterDTO userRegisterDTO) {
        return Users.builder().username(userRegisterDTO.getUsername())
                .password(userRegisterDTO.getPassword())
                .email(userRegisterDTO.getEmail()).build();
    }

}
