package com.stocker.api.domain.mapper;

import com.stocker.api.domain.dto.user.UserRequest;
import com.stocker.api.domain.dto.user.UserResponse;
import com.stocker.api.domain.entity.User;
import com.stocker.api.domain.shared.DefaultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserMapper implements DefaultMapper<User, UserRequest, UserResponse> {
    private final BCryptPasswordEncoder encoder;

    @Override
    public User toDomain(UserRequest user) {
        return User.builder()
                .id(UUID.randomUUID())
                .name(user.name())
                .password(encoder.encode(user.password()))
                .email(user.email())
                .cpf(user.cpf())
                .status(user.status())
                .build();
    }

    @Override
    public UserRequest toRequest(User user) {
        return UserRequest.builder()
                .name(user.getName())
                .password(user.getPassword())
                .email(user.getEmail())
                .cpf(user.getCpf())
                .status(user.getStatus())
                .build();
    }

    @Override
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .email(user.getEmail())
                .cpf(user.getCpf())
                .status(user.getStatus())
                .roles(user.getRoles())
                .profileImage(user.getProfileImage() == null ? null : Base64.getEncoder().encodeToString(user.getProfileImage()))
                .build();
    }
}
