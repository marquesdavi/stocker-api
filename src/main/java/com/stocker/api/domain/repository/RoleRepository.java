package com.stocker.api.domain.repository;

import com.stocker.api.domain.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
