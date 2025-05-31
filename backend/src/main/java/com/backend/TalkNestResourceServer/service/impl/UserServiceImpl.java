package com.backend.TalkNestResourceServer.service.impl;

import com.backend.TalkNestResourceServer.domain.entities.Users;
import com.backend.TalkNestResourceServer.repository.UserProfileRepository;
import com.backend.TalkNestResourceServer.repository.UserRepository;
import com.backend.TalkNestResourceServer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserProfileRepository userProfileRepository;

    @Override
    public Users createUser(Users userDetails) {
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        return userRepository.save(userDetails);
    }

    @Override
    public Users saveUser(Users users) {
        return userRepository.save(users);
    }

    @Override
    public Optional<Users> getById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Users loadedByUsername(String username) {
        Optional<Users> loadedUser = userRepository.findByUsername(username);
        return loadedUser.orElse(null);
    }
}
