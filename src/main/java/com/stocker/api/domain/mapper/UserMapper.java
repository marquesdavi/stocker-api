package com.stocker.api.domain.mapper;

import com.stocker.api.domain.dto.user.UserRequest;
import com.stocker.api.domain.entity.User;
import com.stocker.api.domain.shared.RequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserMapper implements RequestMapper<User, UserRequest> {
    private final BCryptPasswordEncoder encoder;

    @Override
    public User toDomain(UserRequest user) {
        return User.builder()
                .id(UUID.randomUUID())
                .name(user.name())
                .password(encoder.encode(user.password()))
                .email(user.email())
                .build();
    }


    public static UserRequest toResponse(User user) {
        return UserRequest.builder().build();
    }
}
