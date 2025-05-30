package com.backend.TalkNestResourceServer.service;

import com.backend.TalkNestResourceServer.domain.dtos.users.UserRegisterDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    void registerUser(UserRegisterDTO user, HttpServletRequest request);

    boolean verifyEmailToken(String token);
}
