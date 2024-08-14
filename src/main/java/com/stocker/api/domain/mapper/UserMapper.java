package com.stocker.api.domain.mapper;

import com.stocker.api.domain.dto.user.UserRequest;
import com.stocker.api.domain.dto.user.UserResponse;
import com.stocker.api.domain.entity.User;
import com.stocker.api.domain.shared.RequestMapper;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper implements RequestMapper<User, UserRequest> {
    public User toDomain(UserRequest user) {
        return User.builder()
                .id(UUID.randomUUID())
                .name(user.name())
                .password(user.password())
                .email(user.email())
                .build();
    }

    public static UserRequest toResponse(User user) {
        return UserRequest.builder().build();
    }
}
