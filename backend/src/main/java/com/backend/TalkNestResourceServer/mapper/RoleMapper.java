package com.backend.TalkNestResourceServer.mapper;

import com.backend.TalkNestResourceServer.domain.dtos.users.RoleResponse;
import com.backend.TalkNestResourceServer.domain.entities.Role;

import java.util.stream.Collectors;

public class RoleMapper {

    public static RoleResponse mapToRoleResponse(Role role) {
        return RoleResponse.builder().id(role.getId()
                .toString()).name(role.getName())
                .permissions(role.getPermissions().stream()
                        .map(PermissionMapper::mapToPermissionResponse)
                        .collect(Collectors.toSet()))
                .build();
    }

}
