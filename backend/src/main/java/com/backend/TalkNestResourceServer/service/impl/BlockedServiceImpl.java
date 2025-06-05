package com.backend.TalkNestResourceServer.service.impl;

import com.backend.TalkNestResourceServer.domain.dtos.blocked.BlockUserRequest;
import com.backend.TalkNestResourceServer.domain.dtos.blocked.UnblockUserRequest;
import com.backend.TalkNestResourceServer.domain.dtos.users.UserProfileResponse;
import com.backend.TalkNestResourceServer.domain.entities.Blocked;
import com.backend.TalkNestResourceServer.exception.signature.AlreadyBlockedException;
import com.backend.TalkNestResourceServer.exception.signature.UserNotExistsException;
import com.backend.TalkNestResourceServer.mapper.UserProfileMapper;
import com.backend.TalkNestResourceServer.repository.BlockedRepository;
import com.backend.TalkNestResourceServer.repository.UserProfileRepository;
import com.backend.TalkNestResourceServer.service.BlockedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlockedServiceImpl implements BlockedService {

    private final BlockedRepository blockedRepository;

    private final UserProfileRepository userProfileRepository;

    @Override
    public void blockUser(BlockUserRequest request) {
        var loadedBlocker = userProfileRepository.findById(UUID.fromString(request.getBlockerId())).orElseThrow(
                () -> new UserNotExistsException("User not found with id: " + request.getBlockerId().toString())
        );

        var loadedBlocked = userProfileRepository.findById(UUID.fromString(request.getBlockedId())).orElseThrow(
                () -> new UserNotExistsException("User not found with id: " + request.getBlockedId().toString())
        );

        boolean isBlocked = blockedRepository.existsByBlockerIdAndBlockedId(UUID.fromString(request.getBlockerId())
                , UUID.fromString(request.getBlockedId()));
        if(isBlocked) {
            throw new AlreadyBlockedException("You already blocked this user");
        }

        Blocked blocked = Blocked.builder()
                .blockedId(loadedBlocker.getUserId())
                .blockedId(loadedBlocked.getUserId())
                .createdAt(LocalDateTime.now())
                .build();

        blockedRepository.save(blocked);
    }

    @Override
    public void unBlockUser(UnblockUserRequest request) {
        var loadedBlocker = userProfileRepository.findById(UUID.fromString(request.getBlockerId())).orElseThrow(
                () -> new UserNotExistsException("User not found with id: " + request.getBlockerId().toString())
        );

        var loadedBlocked = userProfileRepository.findById(UUID.fromString(request.getBlockedId())).orElseThrow(
                () -> new UserNotExistsException("User not found with id: " + request.getBlockedId().toString())
        );

        Blocked b = blockedRepository.findByBlockerIdAndBlockedId(loadedBlocker.getUserId()
                , loadedBlocked.getUserId()).orElse(null);
        if(b != null) {
            blockedRepository.deleteById(b.getId());
        }
    }

    @Override
    public List<UserProfileResponse> getBlockedById(String userId) {
        userProfileRepository.findById(UUID.fromString(userId)).orElseThrow(
                () -> new UserNotExistsException("User not found with id: " + userId)
        );

        List<Blocked> blockeds = blockedRepository.findByBlockerId(UUID.fromString(userId));
        return blockeds.stream().map(x -> {
            return UserProfileMapper.mapToUserResponse(userProfileRepository.findById(x.getBlockedId()).get());
        }).toList();
    }
}
