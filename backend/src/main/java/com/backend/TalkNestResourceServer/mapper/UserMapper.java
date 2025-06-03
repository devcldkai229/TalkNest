package com.backend.TalkNestResourceServer.mapper;

import com.backend.TalkNestResourceServer.domain.dtos.users.RegisterUserRequest;
import com.backend.TalkNestResourceServer.domain.dtos.users.UserResponse;
import com.backend.TalkNestResourceServer.domain.entities.Users;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@NoArgsConstructor
public class UserMapper {

    public static Users mapToUser(RegisterUserRequest registerUserRequest) {
        return Users.builder().username(registerUserRequest.getUsername())
                .password(registerUserRequest.getPassword())
                .email(registerUserRequest.getEmail()).build();
    }

    public static UserResponse mapToUserResponse(Users users) {
        return UserResponse.builder()
                .username(users.getUsername())
                .email(users.getEmail())
                .authProvider(users.getAuthProvider().toString())
                .createdAt(users.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .updatedAt(users.getUpdatedAt() != null
                        ? users.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        : null)
                .roles(users.getRoles().stream().map(RoleMapper::mapToRoleResponse).collect(Collectors.toSet()))
                .build();
    }



}
