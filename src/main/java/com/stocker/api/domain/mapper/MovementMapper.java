package com.stocker.api.domain.mapper;

import com.stocker.api.domain.dto.movement.MovementRequest;
import com.stocker.api.domain.dto.movement.MovementResponse;
import com.stocker.api.domain.dto.user.UserRequest;
import com.stocker.api.domain.dto.user.UserResponse;
import com.stocker.api.domain.entity.Movement;
import com.stocker.api.domain.entity.User;
import com.stocker.api.domain.shared.DefaultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MovementMapper implements DefaultMapper<Movement, MovementRequest, MovementResponse> {

    @Override
    public Movement toDomain(MovementRequest user) {
        return Movement.builder()
                .id(UUID.randomUUID())

                .build();
    }

    @Override
    public MovementRequest toRequest(Movement movement) {
        return MovementRequest.builder()

                .build();
    }

    @Override
    public MovementResponse toResponse(Movement movement) {
        return MovementResponse.builder()
                .id(movement.getId())
                .userId(movement.getUser().getId())
                .customerId(movement.getCustomer().getId())
                .movementType(movement.getMovementType())
                .date(movement.getDate())
                .totalValue(movement.getTotalValue())
                .totalDiscountValue(movement.getTotalDiscountValue())
                .build();
    }
}
