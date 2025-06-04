package com.backend.TalkNestResourceServer.controller.user;

import com.backend.TalkNestResourceServer.domain.ApiResponse;
import com.backend.TalkNestResourceServer.domain.dtos.blocked.BlockUserRequest;
import com.backend.TalkNestResourceServer.domain.dtos.blocked.UnblockUserRequest;
import com.backend.TalkNestResourceServer.domain.dtos.users.UserProfileResponse;
import com.backend.TalkNestResourceServer.service.BlockedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/users/blocks")
@RequiredArgsConstructor
public class BlockedController {

    private final BlockedService blockedService;

    @PostMapping("/request")
    public ResponseEntity<ApiResponse<String>> sendBlockedRequest(@RequestBody BlockUserRequest request){
        try {
            blockedService.blockUser(request);

            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ApiResponse.<String>builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .responseAt(LocalDateTime.now())
                            .message("").data("").build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<String>builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .responseAt(LocalDateTime.now())
                            .message("").data("").build());
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> unBlockedRequest(@RequestBody UnblockUserRequest request) {
        blockedService.unBlockUser(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.<String>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message("").data("")
                        .responseAt(LocalDateTime.now()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<UserProfileResponse>>> getBlockedByBlockerId(@PathVariable(name = "id") String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<UserProfileResponse>>builder()
                        .data(blockedService.getBlockedById(id))
                        .message("")
                        .responseAt(LocalDateTime.now())
                        .statusCode(HttpStatus.OK.value()).build());
    }


}
