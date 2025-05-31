package com.backend.TalkNestResourceServer.service;

import com.backend.TalkNestResourceServer.domain.entities.Role;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

import java.text.ParseException;
import java.util.Set;

public interface JwtService {

    String generateToken(String subject, boolean isRefresh, Set<Role> claimRoles) throws JOSEException;

    JWTClaimsSet verifyToken(String token) throws JOSEException, ParseException;

    String  extractUsername(String user) throws ParseException;

    Object extractClaim(String token, String claimName) throws ParseException;

}
