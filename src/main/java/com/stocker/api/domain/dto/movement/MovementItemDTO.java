package com.stocker.api.domain.dto.movement;

import java.util.UUID;

public record MovementItemDTO(
        Integer amount,
        UUID product
) {
}
