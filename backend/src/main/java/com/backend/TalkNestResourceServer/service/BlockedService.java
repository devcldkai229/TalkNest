package com.backend.TalkNestResourceServer.service;

import com.backend.TalkNestResourceServer.domain.dtos.followers.BlockUserRequest;
import com.backend.TalkNestResourceServer.domain.dtos.users.UserResponse;

import java.util.List;

public interface BlockedService {

    void blockUser(BlockUserRequest request);

    List<UserResponse> getBlockedById(String userId);
}
