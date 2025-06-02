package com.backend.TalkNestResourceServer.domain.dtos.auths;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecaptchaVerifiedResponse {

    private boolean success;

    private LocalDateTime challengeTs;

    private String hostname;

    private List<String> errorCodes;
}
