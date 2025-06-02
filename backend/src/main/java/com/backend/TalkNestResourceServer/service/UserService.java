package com.backend.TalkNestResourceServer.service;

import com.backend.TalkNestResourceServer.domain.dtos.users.UserResponse;

public interface UserService {

    UserResponse loadByUsername(String username);

}
