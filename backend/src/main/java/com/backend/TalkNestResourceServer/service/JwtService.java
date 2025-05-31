package com.backend.TalkNestResourceServer.service;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.core.userdetails.User;

import java.util.Map;

public interface JwtService {

    String generateToken(String subject, Map<String, Object> claims);


    JWTClaimsSet verifyToken(String token);

    String  extractUsername(User user);

    Object extractClaim(String token, String claimName);

}
