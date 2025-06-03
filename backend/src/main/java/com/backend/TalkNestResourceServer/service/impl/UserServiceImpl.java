package com.backend.TalkNestResourceServer.service.impl;


import com.backend.TalkNestResourceServer.domain.dtos.users.UserResponse;
import com.backend.TalkNestResourceServer.exception.signature.UserNotExistsException;
import com.backend.TalkNestResourceServer.mapper.UserMapper;
import com.backend.TalkNestResourceServer.repository.UserRepository;
import com.backend.TalkNestResourceServer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;


    @Override
    public UserResponse loadByUsername(String username) {
        var loadedUser = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotExistsException("User not found with username: " + username)
        );
        return UserMapper.mapToUserResponse(loadedUser);
    }

}
