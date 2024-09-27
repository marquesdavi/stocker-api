package com.stocker.api.domain.dto.customer;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CustomerRequest implements Serializable {

    @NotBlank
    private String name;

    @NotBlank
    private String cpf;

    @NotNull
    private LocalDate birthDate;

    private BigDecimal totalPurchaseValue;

    private BigDecimal discountPercentage;
}
