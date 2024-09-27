package com.stocker.api.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Document(collection = "customers")
public class Customer implements Serializable {

    @Id
    private UUID id;
    private String name;
    @Indexed(unique = true)
    private String cpf;
    private LocalDate birthDate;
    @Builder.Default
    private LocalDate creationDate = LocalDate.now();
    private BigDecimal totalPurchaseValue;
    private BigDecimal discountPercentage;

    @DBRef
    private List<Movement> movements;

    public int getPurchasesInLastSixMonths() {
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        return (int) movements.stream()
                .filter(movement -> movement.getDate().toLocalDate().isAfter(sixMonthsAgo) && movement.getMovementType() == Movement.MovementType.SALE)
                .count();
    }
}
