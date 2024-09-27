package com.stocker.api.domain.dto.user;

import lombok.Builder;

import java.io.Serializable;
import com.stocker.api.domain.entity.User.UserStatus;

@Builder
public record UserRequest(
        String name,
        String email,
        String cpf,
        String password,
        UserStatus status
) implements Serializable {
}
