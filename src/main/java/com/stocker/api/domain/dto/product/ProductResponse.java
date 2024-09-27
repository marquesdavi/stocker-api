package com.stocker.api.domain.dto.product;

import lombok.Builder;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ProductResponse(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        LocalDate expirationDate,
        BigDecimal productDiscount,
        String category
) implements Serializable {
}
