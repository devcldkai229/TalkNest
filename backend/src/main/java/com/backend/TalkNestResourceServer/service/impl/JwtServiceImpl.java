package com.backend.TalkNestResourceServer.service.impl;

import com.backend.TalkNestResourceServer.domain.entities.Role;
import com.backend.TalkNestResourceServer.domain.helpers.JwtProps;
import com.backend.TalkNestResourceServer.exception.signature.ExpiredTokenException;
import com.backend.TalkNestResourceServer.exception.signature.VerifyTokenFailedException;
import com.backend.TalkNestResourceServer.service.JwtService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

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
    public String generateToken(String subject, boolean isRefreshToken, Set<Role> claimRoles) throws JOSEException {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build();

        JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder()
                .subject(subject)
                .audience(jwtProps.getAudience())
                .issuer(jwtProps.getIssuer())
                .expirationTime(isRefreshToken
                        ? (new Date(System.currentTimeMillis() + jwtProps.getExpirationRefresh()))
                        : new Date(System.currentTimeMillis() + jwtProps.getExpiration()))
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString());

        if (claimRoles != null) {
            claimRoles.forEach(x -> {
                claimsBuilder.claim("roles", x);
                if(x.getPermissions() != null || x.getPermissions().isEmpty()) {
                    claimsBuilder.claim("permissions", x.getPermissions());
                }
            });
        }

        JWTClaimsSet claimsSet = claimsBuilder.build();
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    @Override
    public JWTClaimsSet verifyToken(String token) throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);

        if(!signedJWT.verify(verifier)) {
            throw new VerifyTokenFailedException("Invalid Token");
        }

        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

        if(claimsSet.getExpirationTime() != null && claimsSet.getExpirationTime().before(new Date())) {
            throw new ExpiredTokenException("Expired Token");
        }

        if(!claimsSet.getIssuer().equals(jwtProps.getIssuer())) {
            throw new JOSEException("Invalid JWT Issuer");
        }

        if(!claimsSet.getAudience().equals(jwtProps.getAudience())) {
            throw new JOSEException("Invalid JWT Audience");
        }

        return claimsSet;
    }


    @Override
    public String extractUsername(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            return claimsSet.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object extractClaim(String token, String claimName) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            return claimsSet.getClaim(claimName);
        } catch (Exception e) {
            return null;
        }
    }
}
