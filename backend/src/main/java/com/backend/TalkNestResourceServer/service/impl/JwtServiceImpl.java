package com.backend.TalkNestResourceServer.service.impl;


import com.backend.TalkNestResourceServer.domain.helpers.JwtProps;
import com.backend.TalkNestResourceServer.repository.UserRepository;
import com.backend.TalkNestResourceServer.service.JwtService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    private final JwtProps jwtProps;

    private final JWSSigner signer;

    private final JWSVerifier verifier;

    public JwtServiceImpl(JwtProps jwtProperties) throws JOSEException {
        this.jwtProps = jwtProperties;
        byte[] secretBytes = jwtProperties.getSecret().getBytes();
        this.signer = new MACSigner(secretBytes);
        this.verifier = new MACVerifier(secretBytes);
    }


    @Override
    public String generateToken(String subject, Map<String, Object> claims) {
        return "";
    }

    @Override
    public JWTClaimsSet verifyToken(String token) {
        return null;
    }

    @Override
    public String extractUsername(User user) {
        return "";
    }

    @Override
    public Object extractClaim(String token, String claimName) {
        return null;
    }
}
