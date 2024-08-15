package com.stocker.api.domain.dto.auth;

public record TokenResponse(String accessToken, Long expiresIn) {
}
