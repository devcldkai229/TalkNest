package com.backend.TalkNestResourceServer.service.impl;

import com.backend.TalkNestResourceServer.domain.dtos.auths.AuthenticationRequest;
import com.backend.TalkNestResourceServer.domain.dtos.auths.AuthenticationResponse;
import com.backend.TalkNestResourceServer.domain.dtos.auths.IntrospectResponse;
import com.backend.TalkNestResourceServer.domain.dtos.users.UserRegisterDTO;
import com.backend.TalkNestResourceServer.domain.entities.RefreshToken;
import com.backend.TalkNestResourceServer.domain.entities.Users;
import com.backend.TalkNestResourceServer.domain.entities.VerificationToken;
import com.backend.TalkNestResourceServer.domain.enums.AuthProvider;
import com.backend.TalkNestResourceServer.exception.signature.SendVerificationEmailException;
import com.backend.TalkNestResourceServer.exception.signature.UnauthenticatedException;
import com.backend.TalkNestResourceServer.exception.signature.UserNotExistsException;
import com.backend.TalkNestResourceServer.mapper.UserMapper;
import com.backend.TalkNestResourceServer.repository.RefreshTokenRepository;
import com.backend.TalkNestResourceServer.repository.VerificationTokenRepository;
import com.backend.TalkNestResourceServer.service.AuthenticationService;
import com.backend.TalkNestResourceServer.service.JwtService;
import com.backend.TalkNestResourceServer.service.UserService;
import com.backend.TalkNestResourceServer.util.EmailUtil;
import com.nimbusds.jose.JOSEException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final VerificationTokenRepository verificationTokenRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final EmailUtil emailUtil;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public void registerUser(UserRegisterDTO userRegisterDTO, HttpServletRequest request) {
        Users unSaveUser = UserMapper.mapToUser(userRegisterDTO);
        unSaveUser.setAuthProvider(AuthProvider.LOCAL);

        Users loadedUser = userService.createUser(unSaveUser);
        String verifyToken = emailUtil.generateVerifyToken(loadedUser.getUsername());
        VerificationToken verificationToken = VerificationToken.builder()
                .token(verifyToken).userId(loadedUser.getId())
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(5))
                .build();

        verificationTokenRepository.save(verificationToken);
        try {
            emailUtil.sendRegistrationVerificationEmail(loadedUser, verifyToken, request);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new SendVerificationEmailException(e.getMessage());
        }
    }

    @Override
    public boolean verifyEmailToken(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findById(token);

        if(!verificationTokenOptional.isPresent()) {
            return false;
        }

        VerificationToken verificationToken = verificationTokenOptional.get();

        if(verificationToken.isExpired()) {
            return false;
        }

        Users loadedUser = userService.getById(verificationToken.getUserId()).orElseThrow(
                () -> new UserNotExistsException(String.format("User not found with id: %s!", verificationToken.getUserId()))
        );

        loadedUser.setVerified(true);
        userService.saveUser(loadedUser);
        verificationTokenRepository.delete(verificationToken);
        return true;
    }

    @Override
    public IntrospectResponse introspectAccessToken(String accessToken) {
        boolean isValid = true;
        try {
            jwtService.verifyToken(accessToken);
        } catch (Exception e) {
            isValid = false;
        }

        return IntrospectResponse.builder().isValid(isValid).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest requestLogin) throws JOSEException, ParseException {
        var loadedUser = userService.loadedByUsername(requestLogin.getUsername());

        if(loadedUser == null) {
            throw new UnauthenticatedException("Username invalid!");
        }

        boolean isAuthenticated = passwordEncoder.matches(requestLogin.getPassword(), loadedUser.getPassword());
        if(!isAuthenticated) {
            throw new BadCredentialsException("Password invalid!");
        }

        String accessToken = jwtService.generateToken(requestLogin.getUsername(), false, loadedUser.getRoles());
        String refreshToken = jwtService.generateToken(requestLogin.getUsername(), true, loadedUser.getRoles());


        refreshTokenRepository.save(
                RefreshToken.builder()
                        .token(refreshToken)
                        .createdAt(LocalDateTime.now())
                        .expiredDate(jwtService.verifyToken(refreshToken)
                                .getExpirationTime().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime())
                        .build());

        return AuthenticationResponse.builder()
                .isAuthenticated(true)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public IntrospectResponse introspectRefreshToken(String refreshToken) {
        return null;
    }

    @Override
    public AuthenticationResponse refreshToken(String oldRefreshToken) {
        return null;
    }

    @Override
    public void logout(String token) {

    }
}
