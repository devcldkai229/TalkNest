package com.backend.TalkNestResourceServer.controller;

import com.backend.TalkNestResourceServer.client.RecaptchaClient;
import com.backend.TalkNestResourceServer.domain.ApiResponse;
import com.backend.TalkNestResourceServer.domain.dtos.auths.*;
import com.backend.TalkNestResourceServer.domain.dtos.users.RegisterUserRequest;
import com.backend.TalkNestResourceServer.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    private final RecaptchaClient recaptchaClient;

    @Value("${reCaptcha.secret-key}")
    private String recaptchaSecretKey;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterUserRequest registerUserRequest, HttpServletRequest request) {
        authService.registerUser(registerUserRequest, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<String>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("")
                        .data("Verify your email to complete final step!")
                        .responseAt(LocalDateTime.now())
                        .build());
    }

    @PostMapping("/verify-email")
    public ResponseEntity<ApiResponse<String>> verifyEmail(@RequestParam(name = "token") String token) {
        boolean isVerified = authService.verifyEmailToken(token);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder().build();

        if(!isVerified) {
            apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            apiResponse.setData("Expired verification token or token is not exist!");
        } else {
            apiResponse.setStatusCode(HttpStatus.OK.value());
            apiResponse.setData("Now! Login your account");
        }

        apiResponse.setMessage("");
        apiResponse.setResponseAt(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.OK)
                .body(apiResponse);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<?>> forgotPassword(@RequestBody ForgotPasswordPayLoadRequest request, HttpServletRequest httpServletRequest) {
        RecaptchaVerifiedResponse ggResponse =  recaptchaClient.verify(RecaptchaVerifyRequest.builder()
                .secretKey(recaptchaSecretKey)
                .recaptchaToken(request.getRecaptchaToken())
                .build());

        if(!ggResponse.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.builder().statusCode(HttpStatus.BAD_REQUEST.value())
                            .message("Verify recaptcha failed!").data(null)
                            .responseAt(LocalDateTime.now()).build()
            );
        }
        try {
            authService.sendForgotPasswordEmail(request, httpServletRequest);
            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.builder().statusCode(HttpStatus.OK.value())
                            .message("Sending email to reset password successfully!").data(null)
                            .responseAt(LocalDateTime.now()).build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("INTERNAL_SERVER_ERROR").data(null)
                            .responseAt(LocalDateTime.now()).build()
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest request) throws ParseException, JOSEException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AuthenticationResponse>builder()
                        .statusCode(200)
                        .message("")
                        .data(authService.authenticate(request))
                        .build());
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> refresh(@RequestParam(name = "token") String refreshToken) throws ParseException, JOSEException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AuthenticationResponse>builder()
                        .statusCode(200)
                        .message("")
                        .data(authService.refreshToken(refreshToken))
                        .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestParam(name = "token") String token) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.<String>builder()
                        .statusCode(204)
                        .message("")
                        .data("Logout success!")
                        .responseAt(LocalDateTime.now())
                        .build());
    }


}
