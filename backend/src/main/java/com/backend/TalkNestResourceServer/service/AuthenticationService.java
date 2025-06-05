package com.backend.TalkNestResourceServer.service;

import com.backend.TalkNestResourceServer.domain.dtos.auths.*;
import com.backend.TalkNestResourceServer.domain.dtos.users.RegisterUserRequest;
import com.nimbusds.jose.JOSEException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.UUID;

public interface AuthenticationService {

    void registerUser(RegisterUserRequest user, HttpServletRequest request);

    boolean verifyEmailToken(String token);

    IntrospectResponse introspectAccessToken(String accessToken) throws ParseException, JOSEException;

    AuthenticationResponse authenticate(AuthenticationRequest requestLogin) throws JOSEException, ParseException;

    IntrospectResponse introspectRefreshToken(String refreshToken) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(String oldRefreshToken) throws ParseException, JOSEException;

    void logout(String token) throws ParseException, JOSEException;

    void sendForgotPasswordEmail(ForgotPasswordPayLoadRequest request, HttpServletRequest httpRequest) throws MessagingException, UnsupportedEncodingException;

    void changePassword(UUID userId, ChangeUserPasswordRequest request);
}
