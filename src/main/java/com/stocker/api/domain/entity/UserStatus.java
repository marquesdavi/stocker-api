package com.stocker.api.domain.entity;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE(1L),
    INACTIVE(2L);

    private final Long id;

    UserStatus(Long id) {
        this.id = id;
    }
}