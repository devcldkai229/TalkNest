package com.backend.TalkNestResourceServer.controller;

import com.backend.TalkNestResourceServer.domain.ApiResponse;
import com.backend.TalkNestResourceServer.domain.dtos.users.UserRegisterDTO;
import com.backend.TalkNestResourceServer.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody UserRegisterDTO userRegisterDTO, HttpServletRequest request) {
        authService.registerUser(userRegisterDTO, request);

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


}
