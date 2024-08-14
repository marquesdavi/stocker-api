package com.stocker.api.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.List;
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
}
