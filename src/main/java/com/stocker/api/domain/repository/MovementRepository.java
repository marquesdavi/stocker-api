package com.stocker.api.domain.repository;

import com.stocker.api.domain.entity.Movement;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface MovementRepository extends MongoRepository<Movement, UUID> {
}
