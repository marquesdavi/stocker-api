package com.stocker.api.domain.mapper;

import com.stocker.api.domain.dto.movement.MovementItemDTO;
import com.stocker.api.domain.dto.movement.MovementRequest;
import com.stocker.api.domain.dto.movement.MovementResponse;
import com.stocker.api.domain.entity.Customer;
import com.stocker.api.domain.entity.Movement;
import com.stocker.api.domain.entity.Product;
import com.stocker.api.domain.entity.User;
import com.stocker.api.domain.shared.DefaultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MovementMapper implements DefaultMapper<Movement, MovementRequest, MovementResponse> {

    @Override
    public Movement toDomain(MovementRequest movementRequest) {
        return Movement.builder()
                .id(UUID.randomUUID())
                .movementType(movementRequest.movementType())
                .movementDiscount(movementRequest.movementDiscount() != null ? movementRequest.movementDiscount() : null)
                .products(movementRequest.items().stream()
                        .map(item -> Product.builder()
                                .id(item.product())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public MovementRequest toRequest(Movement movement) {
        return MovementRequest.builder()
                .customerId(movement.getCustomer().getId())
                .movementType(movement.getMovementType())
                .items(movement.getProducts().stream()
                        .map(product -> MovementItemDTO.builder()
                                .product(product.getId())
                                .amount(1)
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public MovementResponse toResponse(Movement movement) {
        return MovementResponse.builder()
                .id(movement.getId())
                .userId(movement.getUser() != null ? movement.getUser().getId() : null)
                .customerId(movement.getCustomer() != null ? movement.getCustomer().getId() : null)
                .movementType(movement.getMovementType())
                .date(movement.getDate())
                .totalValue(movement.getTotalValue())
                .totalDiscountValue(movement.getTotalDiscountValue())

                .build();
    }

}
