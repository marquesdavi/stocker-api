package com.stocker.api.domain.entity;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Document(collection = "movements")
public class Movement implements Persistable<UUID>, Serializable {
    @Id
    @Default
    private final UUID id = UUID.randomUUID();

    @DBRef
    private List<Product> products;

    @DBRef
    private User user;

    @DBRef
    private Customer customer;
    private BigDecimal totalDiscountValue;
    private BigDecimal totalValue;
    private Double movementDiscount;

    private MovementType movementType;
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();

    @Override
    public boolean isNew() {
        return false;
    }

    @Getter
    public enum MovementType {
        PURCHASE(1L),
        SALE(2L);

        private final Long id;

        MovementType(Long id) {
            this.id = id;
        }
    }
}

