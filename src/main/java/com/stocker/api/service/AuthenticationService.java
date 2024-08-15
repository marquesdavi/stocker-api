package com.stocker.api.service;

import com.stocker.api.domain.dto.auth.LoginRequest;
import com.stocker.api.domain.dto.auth.TokenResponse;

public interface AuthenticationService {
    TokenResponse login(LoginRequest request);
}
