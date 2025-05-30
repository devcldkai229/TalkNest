package com.backend.TalkNestResourceServer.service.impl;

import com.backend.TalkNestResourceServer.domain.dtos.users.UserRegisterDTO;
import com.backend.TalkNestResourceServer.domain.entities.Users;
import com.backend.TalkNestResourceServer.domain.entities.VerificationToken;
import com.backend.TalkNestResourceServer.domain.enums.AuthProvider;
import com.backend.TalkNestResourceServer.exception.signature.SendVerificationEmailException;
import com.backend.TalkNestResourceServer.exception.signature.UserNotExistsException;
import com.backend.TalkNestResourceServer.mapper.UserMapper;
import com.backend.TalkNestResourceServer.repository.VerificationTokenRepository;
import com.backend.TalkNestResourceServer.service.AuthenticationService;
import com.backend.TalkNestResourceServer.service.UserService;
import com.backend.TalkNestResourceServer.util.EmailUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final VerificationTokenRepository verificationTokenRepository;

    private final EmailUtil emailUtil;

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
}
