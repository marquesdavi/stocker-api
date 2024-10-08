package com.stocker.api.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Document(collection = "products")
public class Product implements Serializable {

    @Id
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private LocalDate expirationDate;
    private BigDecimal productDiscount;
    private Category category;
    @Builder.Default
    private LocalDate creationDate = LocalDate.now();

    @DBRef
    private List<Movement> movements;
}

