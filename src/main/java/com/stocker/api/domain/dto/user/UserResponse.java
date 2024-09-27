package com.stocker.api.domain.dto.user;

import com.stocker.api.domain.entity.Role;
import lombok.Builder;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import com.stocker.api.domain.entity.User.UserStatus;

@Builder
public record UserResponse(
        UUID id,
        String name,
        String email,
        String cpf,
        String password,
        UserStatus status,
        Set<Role> roles
) implements Serializable {
}
