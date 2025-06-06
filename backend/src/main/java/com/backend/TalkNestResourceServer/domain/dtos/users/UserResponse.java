package com.backend.TalkNestResourceServer.domain.dtos.users;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String username;

    private String email;

    private String authProvider;

    private String createdAt;

    private String updatedAt;

    private Set<RoleResponse> roles;

}
