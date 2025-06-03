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
public class RoleResponse {

    private String id;

    private String name;

    private Set<PermissionResponse> permissions;
}
