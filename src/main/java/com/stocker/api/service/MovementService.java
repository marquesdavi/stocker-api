package com.stocker.api.service;

import com.stocker.api.domain.dto.movement.MovementRequest;
import com.stocker.api.domain.dto.movement.MovementResponse;

import java.util.List;
import java.util.UUID;

public interface MovementService {
    void createMovement(MovementRequest request);
    List<MovementResponse> getMovements();
    MovementResponse getMovementById(UUID id);
    void updateMovement(UUID id, MovementRequest request);
    void deleteMovement(UUID id);
}
