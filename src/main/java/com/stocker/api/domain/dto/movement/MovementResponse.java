package com.stocker.api.domain.dto.movement;


import com.stocker.api.domain.entity.Movement;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record MovementResponse(
        UUID id,
        UUID userId,
        UUID customerId,
        Movement.MovementType movementType,
        BigDecimal totalDiscountValue,
        BigDecimal totalValue,
        LocalDateTime date,
        Double movementDiscount
) implements Serializable {
}
