package com.backend.TalkNestResourceServer.service;


import com.backend.TalkNestResourceServer.domain.entities.Users;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Users createUser(Users userDetails);

    Users saveUser(Users users);

    Optional<Users> getById(UUID id);

    Users loadedByUsername(String username);


}
