package com.backend.TalkNestResourceServer.domain.dtos.auths;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private String accessToken;

    private String refreshToken;

    private boolean isAuthenticated;

}
