package com.backend.TalkNestResourceServer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailServiceConfig {

    @Value("${google.email.from.username}")
    private String emailSender;

    @Value("${google.email.from.application-key}")
    private String applicationKey;

    @Value("${google.email.from.port}")
    private Integer portNumber;

    @Bean
    public JavaMailSender javaMailSenderConfigure() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();

        sender.setHost("smtp.gmail.com");
        sender.setUsername(emailSender);
        sender.setPassword(applicationKey);
        sender.setPort(portNumber);

        Properties properties = sender.getJavaMailProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.timeout", "10000");
        properties.put("mail.smtp.connectiontimeout", "10000");

        return sender;
    }
}
