package com.backend.TalkNestResourceServer.service.impl;

import com.backend.TalkNestResourceServer.domain.dtos.groups.ChangeAvatarRequest;
import com.backend.TalkNestResourceServer.domain.dtos.groups.CreateGroupRequest;
import com.backend.TalkNestResourceServer.domain.dtos.groups.GroupResponse;
import com.backend.TalkNestResourceServer.repository.GroupRepository;
import com.backend.TalkNestResourceServer.repository.UserProfileRepository;
import com.backend.TalkNestResourceServer.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final UserProfileRepository userProfileRepository;

    @Override
    public GroupResponse createNewGroup(CreateGroupRequest request) {
        return null;
    }

    @Override
    public GroupResponse changeAvatarGroup(ChangeAvatarRequest request) {
        return null;
    }
}
