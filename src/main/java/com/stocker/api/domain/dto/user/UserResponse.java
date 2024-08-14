package com.stocker.api.domain.dto.user;

import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

@Builder
public record UserResponse(
        UUID id,
        String name,
        String email,
        String cpf,
        String password
) implements Serializable {
}
