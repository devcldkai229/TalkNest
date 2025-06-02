package com.backend.TalkNestResourceServer.domain.dtos.auths;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecaptchaVerifyRequest {

    private String secretKey;

    private String recaptchaToken;

}
