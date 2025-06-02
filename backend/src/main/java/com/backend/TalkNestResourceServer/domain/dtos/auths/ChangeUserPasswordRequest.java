package com.backend.TalkNestResourceServer.domain.dtos.auths;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeUserPasswordRequest {

    @NotBlank
    private UUID userId;

    @NotBlank
    private String oldPassword;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{6,}",
            message = "Password must be at least 6 characters and include uppercase, lowercase, number and special character")
    @Length(min = 6, message = "Password at least 6 characters")
    private String rawPassword;

    @NotBlank
    private String confirmPassword;

}
