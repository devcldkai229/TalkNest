package com.backend.TalkNestResourceServer.service.impl;


import com.backend.TalkNestResourceServer.service.JwtService;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {



    @Override
    public String generateToken(User user) {
        return "";
    }

    @Override
    public SignedJWT verifyToken(String token) {
        return null;
    }

    @Override
    public String buildScope(User user) {
        return "";
    }
}
