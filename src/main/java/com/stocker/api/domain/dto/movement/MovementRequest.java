package com.stocker.api.domain.dto.movement;

import com.stocker.api.domain.entity.Movement.MovementType;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
public record MovementRequest(
        List<MovementItemDTO> items,
        UUID customerId,
        MovementType movementType
) implements Serializable {

}
