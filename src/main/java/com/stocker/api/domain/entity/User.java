package com.stocker.api.domain.entity;

import com.stocker.api.domain.dto.auth.LoginRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Document(collection = "users")
public class User implements Serializable {

    @Id
    private UUID id;
    private String name;
    private String email;
    private String cpf;
    private String password;

    @DBRef
    private List<Movement> movements;

    @DBRef
    private Set<Role> roles;

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }
}
