package com.backend.TalkNestResourceServer.validation.validator;

import com.backend.TalkNestResourceServer.domain.dtos.users.UserRegisterDTO;
import com.backend.TalkNestResourceServer.validation.interfaces.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object requestDto, ConstraintValidatorContext constraintValidatorContext) {
        if(requestDto instanceof UserRegisterDTO userRegisterDTO) {
            return userRegisterDTO.getPassword() != null
                    && userRegisterDTO.getConfirmPassword().equals(userRegisterDTO.getPassword());
        }
        return false;
    }
}
