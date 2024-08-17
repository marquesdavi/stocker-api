package com.stocker.api.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "movements")
public class Movement {

    @Id
    private UUID id;

    @DBRef
    private List<Product> product;

    @DBRef
    private User user;

    @DBRef
    private Customer customer;

    private String type;
    private Integer quantity;
    private LocalDateTime date;
}

