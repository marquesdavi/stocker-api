package com.stocker.api.domain.entity;

public enum Role {
    ADMIN(1L),
    USER(2L);

    private final long roleId;

    Role(long roleId) {
        this.roleId = roleId;
    }

    public long getRoleId() {
        return roleId;
    }

}
