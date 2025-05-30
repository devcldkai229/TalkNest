package com.backend.TalkNestResourceServer.service;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.userdetails.User;

public interface JwtService {

    String generateToken(User user);

    SignedJWT verifyToken(String token);

    String buildScope(User user);

}
