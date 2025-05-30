package com.backend.TalkNestResourceServer.domain.dtos.users;

import com.backend.TalkNestResourceServer.validation.interfaces.PasswordMatches;
import com.backend.TalkNestResourceServer.validation.interfaces.ValidatePassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class UserRegisterDTO {

    @NotBlank(message = "Username cannot blank!")
    @NotEmpty(message = "Username cannot empty!")
    @Length(min = 5, max = 10, message = "Username must be range 5-10 characters!")
    private String username;

    @ValidatePassword(message = "Password must contains a upper & lower case, a number and special character!")
    @NotBlank(message = "Password cannot blank!")
    @Length(min = 6, message = "Password must be greater 6 characters!")
    @NotEmpty(message = "Password cannot empty!")
    private String password;

    @NotBlank(message = "Password cannot blank!")
    @Length(min = 6, message = "Password must be greater 6 characters!")
    @NotEmpty(message = "Password cannot empty!")
    private String confirmPassword;

    @NotBlank(message = "Email cannot blank!")
    @Email(message = "Email invalid!")
    private String email;
}
