package com.stocker.api.domain.dto.user;

import com.stocker.api.domain.entity.Role;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
public record UserResponse(
        UUID id,
        String name,
        String email,
        String cpf,
        String password,
        Set<Role> roles
) implements Serializable {
}
