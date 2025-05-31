package com.backend.TalkNestResourceServer.domain.helpers;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProps {

    private String secret;

    private long expiration;

    private long expirationRefresh;

    private String issuer;

    private String audience;

}
