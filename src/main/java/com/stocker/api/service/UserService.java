package com.stocker.api.service;

import com.stocker.api.domain.dto.user.UserRequest;
import com.stocker.api.domain.dto.user.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(UserRequest user);

    List<UserResponse> getUsers();

    UserResponse getUser(UUID id);

    UserResponse getCurrentUser();

    void updateUser(UserRequest user, UUID id);

    void deleteUser(UUID id);

    void uploadProfileImage(UUID id, MultipartFile profileImage);
}
