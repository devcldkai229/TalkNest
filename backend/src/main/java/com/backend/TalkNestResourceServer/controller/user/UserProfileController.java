package com.backend.TalkNestResourceServer.controller.user;

import com.backend.TalkNestResourceServer.domain.ApiResponse;
import com.backend.TalkNestResourceServer.domain.dtos.users.profile.ChangeProfileAvatarRequest;
import com.backend.TalkNestResourceServer.domain.dtos.users.profile.ProfileDetailsResponse;
import com.backend.TalkNestResourceServer.domain.dtos.users.profile.UpdateProfileRequest;
import com.backend.TalkNestResourceServer.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("api/users/profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileDetailsResponse>> getProfile(@PathVariable(name = "id") String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ProfileDetailsResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("")
                        .data(profileService.getUserProfile(UUID.fromString(id)))
                        .responseAt(LocalDateTime.now())
                        .build());
    }

    @PutMapping("/update-info/{id}")
    public ResponseEntity<ApiResponse<String>> updateInfoProfile(@PathVariable(name = "id") String id,
                                                                 @RequestBody UpdateProfileRequest request) {
        profileService.updateUserProfile(UUID.fromString(id), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<String>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .responseAt(LocalDateTime.now())
                        .message("")
                        .data("")
                        .build());
    }

    @PatchMapping("/change-avatar/{id}")
    public ResponseEntity<ApiResponse<ProfileDetailsResponse>> changeAvatar(@PathVariable(name = "id") String id,
                                                                            @RequestPart("file") MultipartFile file) {
        ChangeProfileAvatarRequest request = ChangeProfileAvatarRequest.builder()
                .file(file).folder("avatars").overwrite(true).build();

        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.<ProfileDetailsResponse>builder()
                            .data(profileService.changeProfileAvatar(UUID.fromString(id), request))
                            .message("")
                            .statusCode(HttpStatus.OK.value())
                            .build());

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<ProfileDetailsResponse>builder()
                            .data(null)
                            .message("")
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .build());
        }
    }


}
