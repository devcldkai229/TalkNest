package com.backend.TalkNestResourceServer.client;

import com.backend.TalkNestResourceServer.domain.dtos.auths.RecaptchaVerifiedResponse;
import com.backend.TalkNestResourceServer.domain.dtos.auths.RecaptchaVerifyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "recaptcha-client", url = "https://www.google.com/recaptcha/api/siteverify")
public interface RecaptchaClient {

    @PostMapping
    RecaptchaVerifiedResponse verify(@RequestBody RecaptchaVerifyRequest request);

}
