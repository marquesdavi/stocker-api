package com.stocker.api.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private UUID id;
    private String name;
    private String description;
    private BigDecimal currentPrice;
    private BigDecimal promotionalPrice;
    private Integer stockQuantity;
    private Category category;

    @DBRef
    private List<Movement> movements;
}

