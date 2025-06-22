package com.backend.TalkNestResourceServer.service;

import com.backend.TalkNestResourceServer.domain.dtos.groups.ChangeAvatarRequest;
import com.backend.TalkNestResourceServer.domain.dtos.groups.CreateGroupRequest;
import com.backend.TalkNestResourceServer.domain.dtos.groups.GroupResponse;

public interface GroupService {

    GroupResponse createNewGroup(CreateGroupRequest request);

    GroupResponse changeAvatarGroup(ChangeAvatarRequest request);




}
