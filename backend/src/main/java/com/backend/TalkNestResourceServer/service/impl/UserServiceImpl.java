package com.backend.TalkNestResourceServer.service.impl;


import com.backend.TalkNestResourceServer.domain.dtos.users.UserResponse;
import com.backend.TalkNestResourceServer.repository.UserRepository;
import com.backend.TalkNestResourceServer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponse loadByUsername(String username) {
        return null;
    }

}
