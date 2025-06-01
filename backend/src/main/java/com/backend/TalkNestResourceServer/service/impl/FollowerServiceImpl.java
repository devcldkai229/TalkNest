package com.backend.TalkNestResourceServer.service.impl;

import com.backend.TalkNestResourceServer.domain.dtos.followers.BlockUserRequest;
import com.backend.TalkNestResourceServer.domain.dtos.followers.FollowerListRequest;
import com.backend.TalkNestResourceServer.domain.dtos.followers.FollowingListRequest;
import com.backend.TalkNestResourceServer.domain.dtos.followers.UnfollowUserRequest;
import com.backend.TalkNestResourceServer.domain.dtos.users.UserProfileResponse;
import com.backend.TalkNestResourceServer.service.FollowerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowerServiceImpl implements FollowerService {


    @Override
    public void sendFollowRequest(FollowingListRequest request) {

    }

    @Override
    public void unfollowUser(UnfollowUserRequest request) {

    }

    @Override
    public void blockUser(BlockUserRequest request) {

    }

    @Override
    public List<UserProfileResponse> getFollowingList(FollowingListRequest request) {
        return List.of();
    }

    @Override
    public List<UserProfileResponse> getFollowerList(FollowerListRequest request) {
        return List.of();
    }
}
