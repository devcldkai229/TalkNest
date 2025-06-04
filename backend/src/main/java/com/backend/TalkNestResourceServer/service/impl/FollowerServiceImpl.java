package com.backend.TalkNestResourceServer.service.impl;

import com.backend.TalkNestResourceServer.domain.dtos.followers.FollowingUsersRequest;
import com.backend.TalkNestResourceServer.domain.dtos.followers.UnfollowUserRequest;
import com.backend.TalkNestResourceServer.domain.dtos.users.UserProfileResponse;
import com.backend.TalkNestResourceServer.domain.entities.Follower;
import com.backend.TalkNestResourceServer.domain.entities.Users;
import com.backend.TalkNestResourceServer.exception.signature.AlreadyFollowingException;
import com.backend.TalkNestResourceServer.exception.signature.UserNotExistsException;
import com.backend.TalkNestResourceServer.mapper.UserProfileMapper;
import com.backend.TalkNestResourceServer.repository.FollowerRepository;
import com.backend.TalkNestResourceServer.repository.UserProfileRepository;
import com.backend.TalkNestResourceServer.repository.UserRepository;
import com.backend.TalkNestResourceServer.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService {

    private final FollowerRepository followerRepository;

    private final UserProfileRepository userProfileRepository;

    private final UserRepository userRepository;

    @Override
    public void sendFollowRequest(FollowingUsersRequest request) {
        var follower = userProfileRepository.findById(UUID.fromString(request.getFollower())).orElseThrow(
                () -> new UserNotExistsException("Follower not found with id: " + request.getFollower())
        );

        var followed = userProfileRepository.findById(UUID.fromString(request.getFollowed())).orElseThrow(
                () -> new UserNotExistsException("Followed not found with id: " + request.getFollowed())
        );

        boolean isFollowed = followerRepository.isExistByFollowerIdAndFollowedId(follower.getUserId(), followed.getUserId());
        if(isFollowed) {
            throw new AlreadyFollowingException("You are already following this user");
        }

        Follower newFollower = Follower.builder()
                .followerId(follower.getUserId())
                .followedId(followed.getUserId())
                .build();
        followerRepository.save(newFollower);
    }

    @Override
    public void unfollowUser(UnfollowUserRequest request) {
        var follower = userProfileRepository.findById(UUID.fromString(request.getFollower())).orElseThrow(
                () -> new UserNotExistsException("Follower not found with id: " + request.getFollower())
        );

        var followed = userProfileRepository.findById(UUID.fromString(request.getFollowed())).orElseThrow(
                () -> new UserNotExistsException("Followed not found with id: " + request.getFollowed())
        );

        boolean isFollowed = followerRepository.isExistByFollowerIdAndFollowedId(follower.getUserId(), followed.getUserId());
        if(isFollowed) return;

        Follower loadedFollower =  followerRepository.findByFollowerIdAndFollowedId(follower.getUserId(), followed.getUserId()).orElse(null);
        if(loadedFollower != null) {
            followerRepository.deleteById(loadedFollower.getId());
        }
    }

    @Override
    public List<UserProfileResponse> getFollowingList(String followerId) {
        var loadedUser = userProfileRepository.findById(UUID.fromString(followerId)).orElseThrow(
                () -> new UserNotExistsException("User not found with id: " + followerId)
        );

        List<Follower> followedList = followerRepository.findByFollowerId(UUID.fromString(followerId));
        return followedList.stream().map(x -> {
            return UserProfileMapper.mapToUserResponse(userProfileRepository.findById(x.getFollowedId()).get());
        }).toList();

    }

    @Override

    public List<UserProfileResponse> getFollowerList(String followedId) {
        var loadedUser = userProfileRepository.findById(UUID.fromString(followedId)).orElseThrow(
                () -> new UserNotExistsException("User not found with id: " + followedId)
        );

        List<Follower> followedList = followerRepository.findByFollowedId(UUID.fromString(followedId));
        return followedList.stream().map(x -> {
            return UserProfileMapper.mapToUserResponse(userProfileRepository.findById(x.getFollowerId()).get());
        }).toList();
    }
}
