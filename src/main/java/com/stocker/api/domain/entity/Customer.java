package com.stocker.api.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "customers")
public class Customer {

    @Id
    private UUID id;
    private String name;
    @Indexed(unique = true)
    private String cpf;
    private LocalDate birthDate;
    private LocalDate creationDate;
    private BigDecimal totalPurchaseValue;
    private BigDecimal discountPercentage;

    @DBRef
    private List<Movement> movements;
}
