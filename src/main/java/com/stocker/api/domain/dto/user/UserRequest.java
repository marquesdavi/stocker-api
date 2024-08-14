package com.stocker.api.domain.dto.user;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record UserRequest(
        String name,
        String email,
        String cpf,
        String password
) implements Serializable {
}
