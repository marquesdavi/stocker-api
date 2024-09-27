package com.stocker.api.domain.dto.movement;

import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

@Builder
public record MovementItemDTO(
        Integer amount,
        UUID product
) implements Serializable {
}
