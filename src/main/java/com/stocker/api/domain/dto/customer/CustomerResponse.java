package com.stocker.api.domain.dto.customer;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class CustomerResponse implements Serializable {

    private UUID id;
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private LocalDate creationDate;
    private BigDecimal totalPurchaseValue;
    private BigDecimal discountPercentage;
}
