package com.stocker.api.domain.dto.movement;

import com.stocker.api.domain.entity.Movement.MovementType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record MovementRequest(
        List<MovementItemDTO> items,
        UUID customerId,
        MovementType movementType
) {

}
