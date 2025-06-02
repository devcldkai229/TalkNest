package com.backend.TalkNestResourceServer.domain.dtos.auths;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordPayLoadRequest {

    @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Email Invalid")
    @NotBlank
    private String email;

    @NotBlank(message = "Captcha verify failed!")
    private String recaptchaToken;

}
