package com.stocker.api.domain.repository;

import com.stocker.api.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
    Optional<User> findByCpf(String cpf);
}
