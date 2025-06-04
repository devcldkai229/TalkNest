package com.backend.TalkNestResourceServer.controller.user;

import com.backend.TalkNestResourceServer.domain.ApiResponse;
import com.backend.TalkNestResourceServer.domain.dtos.users.profile.ProfileDetailsResponse;
import com.backend.TalkNestResourceServer.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users/profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private ProfileService profileService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileDetailsResponse>> getProfile(@PathVariable(name = "id") String id) {
        return null;
    }

}
