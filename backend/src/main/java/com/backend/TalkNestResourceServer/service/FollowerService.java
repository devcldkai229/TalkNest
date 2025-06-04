package com.backend.TalkNestResourceServer.service;

import com.backend.TalkNestResourceServer.domain.dtos.followers.FollowingUsersRequest;
import com.backend.TalkNestResourceServer.domain.dtos.followers.UnfollowUserRequest;
import com.backend.TalkNestResourceServer.domain.dtos.users.UserProfileResponse;

import java.util.List;

public interface FollowerService {

    void sendFollowRequest(FollowingUsersRequest request);

    void unfollowUser(UnfollowUserRequest request);

    List<UserProfileResponse> getFollowingList(String followerId); // đang theo dõi người khác

    List<UserProfileResponse> getFollowerList(String followedId); // được người khác theo dõi

}

