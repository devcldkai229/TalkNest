package com.backend.TalkNestResourceServer.service;

import com.backend.TalkNestResourceServer.domain.dtos.auths.AuthenticationRequest;
import com.backend.TalkNestResourceServer.domain.dtos.auths.AuthenticationResponse;
import com.backend.TalkNestResourceServer.domain.dtos.auths.IntrospectResponse;
import com.backend.TalkNestResourceServer.domain.dtos.users.UserRegisterDTO;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;

public interface AuthenticationService {

    void registerUser(UserRegisterDTO user, HttpServletRequest request);

    boolean verifyEmailToken(String token);

    IntrospectResponse introspectAccessToken(String accessToken) throws ParseException, JOSEException;

    AuthenticationResponse authenticate(AuthenticationRequest requestLogin) throws JOSEException, ParseException;

    IntrospectResponse introspectRefreshToken(String refreshToken);

    AuthenticationResponse refreshToken(String oldRefreshToken);

    void logout(String token);
}
