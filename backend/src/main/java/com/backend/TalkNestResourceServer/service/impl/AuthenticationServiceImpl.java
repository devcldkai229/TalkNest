package com.backend.TalkNestResourceServer.service.impl;

import com.backend.TalkNestResourceServer.constant.ApplicationConstant;
import com.backend.TalkNestResourceServer.domain.dtos.auths.*;
import com.backend.TalkNestResourceServer.domain.dtos.users.RegisterUserRequest;
import com.backend.TalkNestResourceServer.domain.entities.*;
import com.backend.TalkNestResourceServer.domain.enums.AuthProvider;
import com.backend.TalkNestResourceServer.domain.enums.Gender;
import com.backend.TalkNestResourceServer.exception.common.NotFoundException;
import com.backend.TalkNestResourceServer.exception.signature.*;
import com.backend.TalkNestResourceServer.mapper.UserMapper;
import com.backend.TalkNestResourceServer.repository.*;
import com.backend.TalkNestResourceServer.service.AuthenticationService;
import com.backend.TalkNestResourceServer.service.EmailService;
import com.backend.TalkNestResourceServer.service.JwtService;
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
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    private final InvalidateTokenRepository invalidateTokenRepository;

    private final JwtService jwtService;

    private final RoleRepository roleRepository;

    @Override
    public void registerUser(RegisterUserRequest registerUserRequest, HttpServletRequest request) {
        Users unSaveUser = UserMapper.mapToUser(registerUserRequest);
        unSaveUser.setAuthProvider(AuthProvider.LOCAL);

        unSaveUser.setPassword(passwordEncoder.encode(unSaveUser.getPassword()));
        Users loadedUser = userRepository.save(unSaveUser);
        String verifyToken = emailService.generateVerifyToken(loadedUser.getUsername());
        VerificationToken verificationToken = VerificationToken.builder()
                .token(verifyToken).userId(loadedUser.getId())
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(5))
                .build();

        verificationTokenRepository.save(verificationToken);
        try {
            emailService.sendRegistrationVerificationEmail(loadedUser, verifyToken, request);
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

        Users loadedUser = userRepository.findById(verificationToken.getUserId()).orElseThrow(
                () -> new UserNotExistsException(String.format("User not found with id: %s!", verificationToken.getUserId()))
        );

        UserProfile profile = UserProfile.builder()
                .userId(loadedUser.getId())
                .address("#NoData")
                .firstName("#User")
                .lastName(loadedUser.getId().toString().substring(0,5))
                .bio("#NoData")
                .gender(Gender.Unknown)
                .phoneNumber("#NoData")
                .dayOfBirth(null)
                .lastUpdated(LocalDateTime.now())
                .avatarUrl(ApplicationConstant.DEFAULT_AVATAR)
                .build();

        loadedUser.setVerified(true);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("USER").orElse(null));
        loadedUser.setRoles(roles);
        userRepository.save(loadedUser);
        userProfileRepository.save(profile);
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
        var loadedUser = userRepository.findByUsername(requestLogin.getUsername()).orElseThrow(
                () -> new FailedAuthenticatedException("Un authentication user with username: " + requestLogin.getUsername())
        );

        if(!preAuthenticationCheck(loadedUser)) {
            throw new UnauthenticatedException("Account is not valid!");
        }

        boolean isAuthenticated = passwordEncoder.matches(requestLogin.getPassword(), loadedUser.getPassword());
        if(!isAuthenticated) {
            throw new BadCredentialsException("Password invalid!");
        }

        String accessToken = jwtService.generateToken(requestLogin.getUsername(), false, loadedUser.getRoles());
        String refreshToken = jwtService.generateToken(requestLogin.getUsername(), true, loadedUser.getRoles());
        RefreshToken refreshTokenModel = RefreshToken.builder()
                .userId(loadedUser.getId())
                .token(refreshToken)
                .createdAt(LocalDateTime.now())
                .expiredDate(jwtService.verifyToken(refreshToken)
                        .getExpirationTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .build();

        refreshTokenRepository.save(refreshTokenModel);

        return AuthenticationResponse.builder()
                .isAuthenticated(true)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public IntrospectResponse introspectRefreshToken(String refreshToken) throws ParseException, JOSEException {
        RefreshToken loadedRefreshToken = refreshTokenRepository.findByToken(refreshToken).orElseThrow(
                () -> new NotFoundException("Refresh token not exist!")
        );

        if(loadedRefreshToken.getCreatedAt().isBefore(LocalDateTime.now())) {
            return IntrospectResponse.builder().isValid(false).build();
        }

        return IntrospectResponse.builder().isValid(true).build();
    }

    @Override
    public AuthenticationResponse refreshToken(String oldRefreshToken) throws ParseException, JOSEException {
        var username = jwtService.extractUsername(oldRefreshToken);

        RefreshToken loadedToken = refreshTokenRepository.findByToken(oldRefreshToken).orElse(null);
        if(loadedToken == null) {
            throw new NotFoundException("Refresh token invalid");
        }

        var loadedUser = userRepository.findByUsername(username).orElseThrow(
                () -> new FailedAuthenticatedException("Un authentication user with username: " + username)
        );

        String accessToken = jwtService.generateToken(username, false, loadedUser.getRoles());
        String refreshToken = jwtService.generateToken(username, true, loadedUser.getRoles());

        refreshTokenRepository.save(RefreshToken.builder()
                .userId(loadedUser.getId())
                .token(refreshToken)
                .expiredDate(jwtService.verifyToken(refreshToken)
                        .getExpirationTime()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .createdAt(LocalDateTime.now())
                .build());

        refreshTokenRepository.deleteByToken(oldRefreshToken);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isAuthenticated(true)
                .build();
    }

    @Override
    public void logout(String token) throws ParseException, JOSEException {
        var claimSet = jwtService.verifyToken(token);

        if (claimSet == null) {
            throw new UnauthenticatedException("Not perform this action");
        }

        InvalidateToken invalidateToken = InvalidateToken.builder()
                .id(claimSet.getJWTID())
                .token(token)
                .expiryAt(claimSet.getExpirationTime()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .build();

        invalidateTokenRepository.save(invalidateToken);
    }

    @Override
    public void sendForgotPasswordEmail(ForgotPasswordPayLoadRequest request, HttpServletRequest httpRequest) throws MessagingException, UnsupportedEncodingException {
        var loadedUser = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UserNotExistsException("User not found with email: " + request.getEmail())
        );

        String resetToken = emailService.generateVerifyToken(loadedUser.getUsername());
        VerificationToken verificationToken = VerificationToken.builder().token(resetToken)
                .userId(loadedUser.getId())
                .createdAt(LocalDateTime.now()
                        .plusMinutes(5))
                .build();

        verificationTokenRepository.save(verificationToken);
        emailService.sendResetPasswordEmail(loadedUser, resetToken, httpRequest);
    }

    @Override
    public void changePassword(UUID userId, ChangeUserPasswordRequest request) {
        var loadedUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotExistsException("Not found user with id: "+ userId)
        );

        if(!passwordEncoder.matches(request.getOldPassword(), loadedUser.getPassword())) {
            throw new BadCredentialsException("Not matches password!");
        }

        if(!request.getRawPassword().equals(request.getRawPassword())) {
            throw new ConfirmPasswordNotMatchException("Confirm password not matches with new password");
        }

        loadedUser.setPassword(passwordEncoder.encode(request.getRawPassword()));
    }

    private boolean preAuthenticationCheck(Users user) {
        if(!user.isAccountNonExpired()) {
            return false;
        }

        if(!user.isAccountNonLocked()) {
            return false;
        }

        if(!user.isEnabled()) {
            return false;
        }

        if(!user.isCredentialsNonExpired()) {
            return false;
        }

        if(!user.isVerified()) {
            return false;
        }

        return true;
    }
}
