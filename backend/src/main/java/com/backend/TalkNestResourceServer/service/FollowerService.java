package com.backend.TalkNestResourceServer.service;

import com.backend.TalkNestResourceServer.domain.dtos.followers.BlockUserRequest;
import com.backend.TalkNestResourceServer.domain.dtos.followers.FollowerListRequest;
import com.backend.TalkNestResourceServer.domain.dtos.followers.FollowingListRequest;
import com.backend.TalkNestResourceServer.domain.dtos.followers.UnfollowUserRequest;
import com.backend.TalkNestResourceServer.domain.dtos.users.UserProfileResponse;

import java.util.List;

public interface FollowerService {

    void sendFollowRequest(FollowingListRequest request);

    void unfollowUser(UnfollowUserRequest request);

    void blockUser(BlockUserRequest request);

    List<UserProfileResponse> getFollowingList(FollowingListRequest request);

    List<UserProfileResponse> getFollowerList(FollowerListRequest request);



}

