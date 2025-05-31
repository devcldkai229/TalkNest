package com.backend.TalkNestResourceServer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtProps {

    private String secret;

    private long expiration;

    private long expirationRefresh;

    private String issuer;

    private String audience;

}
