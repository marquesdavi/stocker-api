package com.stocker.api.service;

import com.stocker.api.domain.dto.user.UserRequest;
import com.stocker.api.domain.dto.user.UserResponse;
import com.stocker.api.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(UserRequest user);
    List<User> getUsers();
    UserResponse getUser(UUID id);
    void updateUser(UserRequest user, UUID id);
    void deleteUser(UUID id);
}
