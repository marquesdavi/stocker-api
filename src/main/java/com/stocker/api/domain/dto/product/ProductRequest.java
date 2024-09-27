package com.stocker.api.domain.dto.product;

import lombok.Builder;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record ProductRequest(
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        LocalDate expirationDate,
        BigDecimal productDiscount,
        String category
) implements Serializable {
}
