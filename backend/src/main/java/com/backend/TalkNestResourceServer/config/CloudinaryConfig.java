package com.backend.TalkNestResourceServer.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryConfig {

    private String cloudName;

    private String apiKey;

    private String apiSecret;

    @Bean
    public Cloudinary defaultCloudinaryConfig() {
        Map<String, String> apiCloud = ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
        );

        return new Cloudinary(apiCloud);
    }

}
