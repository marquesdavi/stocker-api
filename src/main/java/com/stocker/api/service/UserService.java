package com.stocker.api.service;

import com.stocker.api.domain.dto.user.UserRequest;
import com.stocker.api.domain.entity.User;

import java.util.List;

public interface UserService {
    void createUser(UserRequest user);
    List<User> getUsers();
    User getUser(int id);
    void updateUser(UserRequest user);
    void deleteUser(int id);
}
