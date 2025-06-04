package com.backend.TalkNestResourceServer.service;

import com.backend.TalkNestResourceServer.domain.dtos.blocked.BlockUserRequest;
import com.backend.TalkNestResourceServer.domain.dtos.blocked.UnblockUserRequest;
import com.backend.TalkNestResourceServer.domain.dtos.users.UserProfileResponse;

import java.util.List;

public interface BlockedService {

    void blockUser(BlockUserRequest request);

    void unBlockUser(UnblockUserRequest request);

    List<UserProfileResponse> getBlockedById(String userId);
}
