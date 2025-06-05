package com.backend.TalkNestResourceServer.controller.user;

import com.backend.TalkNestResourceServer.domain.ApiResponse;
import com.backend.TalkNestResourceServer.domain.dtos.followers.FollowingUsersRequest;
import com.backend.TalkNestResourceServer.domain.dtos.followers.UnfollowUserRequest;
import com.backend.TalkNestResourceServer.domain.dtos.users.UserProfileResponse;
import com.backend.TalkNestResourceServer.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users/followings")
public class FollowerController {

    private final FollowerService followerService;

    @PostMapping("/request")
    public ResponseEntity<ApiResponse<String>> sendFollowRequest(@RequestBody FollowingUsersRequest request) {
        try {
            followerService.sendFollowRequest(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.<String>builder()
                            .statusCode(HttpStatus.CREATED.value())
                            .message("Follow request sent successfully")
                            .data("")
                            .responseAt(LocalDateTime.now())
                            .build());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<String>builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .message("Follow request sent not successfully")
                            .data("")
                            .responseAt(LocalDateTime.now())
                            .build());
        }
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> unFollow(@RequestBody UnfollowUserRequest request) {
        followerService.unfollowUser(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.<String>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message("Unfollowed successfully")
                        .data("")
                        .responseAt(LocalDateTime.now())
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<UserProfileResponse>>> getFollowingList(@PathVariable(name = "id") String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<UserProfileResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("")
                        .data(followerService.getFollowingList(id)).build());
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<ApiResponse<List<UserProfileResponse>>> getFollowers(@PathVariable(name = "id") String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<UserProfileResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("")
                        .data(followerService.getFollowerList(id)).build());
    }

}
