package com.backend.TalkNestResourceServer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "jwt")
@Configuration
public class JwtPropertiesConfig {

    private String secret;

    private long expiration;

    private long expirationRefresh;

    private String issuer;

    private String audience;

}
