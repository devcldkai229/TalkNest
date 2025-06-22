package com.backend.TalkNestResourceServer.service.authorization;

import com.backend.TalkNestResourceServer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("profileAuthorization")
@RequiredArgsConstructor
public class ProfileAuthorization {

    private final UserRepository userRepository;

    public boolean canAccessProfile(String id) {
        UUID userId = UUID.fromString(id);

        return userRepository.findById(userId).map(x -> x.getId().equals(userId)).orElse(false);
    }

}
