package com.backend.TalkNestResourceServer.service.impl;

import com.backend.TalkNestResourceServer.domain.dtos.followers.BlockUserRequest;
import com.backend.TalkNestResourceServer.domain.dtos.users.UserResponse;
import com.backend.TalkNestResourceServer.repository.BlockedRepository;
import com.backend.TalkNestResourceServer.service.BlockedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockedServiceImpl implements BlockedService {

    private final BlockedRepository blockedRepository;

    @Override
    public void blockUser(BlockUserRequest request) {

    }

    @Override
    public List<UserResponse> getBlockedById(String userId) {
        return List.of();
    }
}
