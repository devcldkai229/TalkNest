package com.backend.TalkNestResourceServer.util;

import com.backend.TalkNestResourceServer.domain.entities.Users;
import com.nimbusds.jose.util.Base64;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmailUtil {

    @Value("${google.email.from.username}")
    private String from;

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine templateEngine;

    public String generateVerifyToken(String username) {
        return Base64.encode(String.format("%d%s%s", System.currentTimeMillis(), username, UUID.randomUUID())).toString();
    }

    public void sendRegistrationVerificationEmail(Users user, String token, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        Context context = new org.thymeleaf.context.Context();
        String verifyUrl = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString())
                .replacePath("api/auth/verify-email").queryParam("token", token).build().toString();

        context.setVariable("username", user.getUsername());
        context.setVariable("verifyUrl", verifyUrl);
        String htmlContent = templateEngine.process("RegistrationVerificationEmail.html", context);

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject("Verify account!");
        mimeMessageHelper.setText(htmlContent, true);
        mimeMessageHelper.setFrom(from, "TalkNest - Social Platform");

        emailSender.send(mimeMessage);
    }

}
