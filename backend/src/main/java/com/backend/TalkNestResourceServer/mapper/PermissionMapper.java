package com.backend.TalkNestResourceServer.mapper;

import com.backend.TalkNestResourceServer.domain.dtos.users.PermissionResponse;
import com.backend.TalkNestResourceServer.domain.entities.Permission;

public class PermissionMapper {


    public static PermissionResponse mapToPermissionResponse(Permission permission) {
        return PermissionResponse.builder().id(permission.getId()).name(permission.getName()).build();
    }
}
