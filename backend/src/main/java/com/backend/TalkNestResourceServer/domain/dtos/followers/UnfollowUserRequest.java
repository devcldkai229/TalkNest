package com.backend.TalkNestResourceServer.domain.dtos.followers;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnfollowUserRequest {

    private String follower;

    private String followed;


}
