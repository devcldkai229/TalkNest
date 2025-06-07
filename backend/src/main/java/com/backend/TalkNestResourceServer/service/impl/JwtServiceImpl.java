package com.backend.TalkNestResourceServer.service.impl;

import com.backend.TalkNestResourceServer.domain.entities.Permission;
import com.backend.TalkNestResourceServer.domain.entities.Role;
import com.backend.TalkNestResourceServer.config.JwtPropertiesConfig;
import com.backend.TalkNestResourceServer.exception.signature.ExpiredTokenException;
import com.backend.TalkNestResourceServer.exception.signature.VerifyTokenFailedException;
import com.backend.TalkNestResourceServer.service.JwtService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

    private final JwtPropertiesConfig jwtPropertiesConfig;

    private final JWSSigner signer;

    private final JWSVerifier verifier;

    public JwtServiceImpl(JwtPropertiesConfig jwtProperties) throws JOSEException {
        this.jwtPropertiesConfig = jwtProperties;
        byte[] secretBytes = jwtProperties.getSecret().getBytes();
        this.signer = new MACSigner(secretBytes);
        this.verifier = new MACVerifier(secretBytes);
    }


    @Override
    public String generateToken(String subject, boolean isRefreshToken, Set<Role> claimRoles) throws JOSEException {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build();

        JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder()
                .subject(subject)
                .audience(jwtPropertiesConfig.getAudience())
                .issuer(jwtPropertiesConfig.getIssuer())
                .expirationTime(isRefreshToken
                        ? (new Date(System.currentTimeMillis() + jwtPropertiesConfig.getExpirationRefresh()))
                        : new Date(System.currentTimeMillis() + jwtPropertiesConfig.getExpiration()))
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString());


        if (!isRefreshToken && (claimRoles != null && !claimRoles.isEmpty())) {
            List<String> roles = claimRoles.stream().map(Role::getName).toList();
            claimsBuilder.claim("roles", roles);

            Set<String> allPermissions = new HashSet<>();
            for (Role role: claimRoles) {
                if(role.getPermissions() != null && !role.getPermissions().isEmpty()) {
                    Set<String> permissions = role.getPermissions()
                            .stream()
                            .map(Permission::getName)
                            .collect(Collectors.toSet());
                    allPermissions.addAll(permissions);
                }
            }

            if(!allPermissions.isEmpty()) {
                claimsBuilder.claim("permissions", new ArrayList<>(allPermissions));
            }
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

        if(claimsSet.getExpirationTime() == null && claimsSet.getExpirationTime().before(new Date())) {
            throw new ExpiredTokenException("Expired Token");
        }

        if(!claimsSet.getIssuer().equals(jwtPropertiesConfig.getIssuer())) {
            throw new JOSEException("Invalid JWT Issuer");
        }

        if(claimsSet.getAudience().stream().noneMatch(x -> x.equals(jwtPropertiesConfig.getAudience()))) {
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

    @Override
    public Collection<? extends GrantedAuthority> extractAuthorities(String token) {
        try {
            JWTClaimsSet claimsSet = verifyToken(token);
            return extractAuthoritiesFromClaims(claimsSet);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private Collection<? extends GrantedAuthority> extractAuthoritiesFromClaims(JWTClaimsSet claimsSet) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        try {
            List<String> roles = claimsSet.getStringListClaim("roles");
            if(roles != null) {
                roles.forEach(x -> authorities.add(new SimpleGrantedAuthority("ROLE_" + x)));
            }
        } catch (ParseException e) {
            try {
                String role = claimsSet.getStringClaim("roles");
                if(role != null) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                }
            } catch (ParseException ex) {}
        }

        try {
            List<String> permissions = claimsSet.getStringListClaim("permissions");
            if(permissions != null) {
                permissions.forEach(x -> authorities.add(new SimpleGrantedAuthority(x)));
            }
        } catch (ParseException e) {
            try {
                String permission = claimsSet.getStringClaim("permissions");
                if(permission != null) {
                    authorities.add(new SimpleGrantedAuthority(permission));
                }
            } catch (ParseException ex) {}
        }

        return authorities;
    }
}

