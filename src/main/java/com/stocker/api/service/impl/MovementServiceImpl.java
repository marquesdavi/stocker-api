package com.stocker.api.service.impl;

import com.stocker.api.domain.dto.movement.MovementItemDTO;
import com.stocker.api.domain.dto.movement.MovementRequest;
import com.stocker.api.domain.dto.movement.MovementResponse;
import com.stocker.api.domain.entity.Customer;
import com.stocker.api.domain.entity.Movement;
import com.stocker.api.domain.entity.Product;
import com.stocker.api.domain.entity.User;
import com.stocker.api.domain.repository.CustomerRepository;
import com.stocker.api.domain.repository.MovementRepository;
import com.stocker.api.domain.repository.ProductRepository;
import com.stocker.api.domain.shared.DefaultMapper;
import com.stocker.api.exception.exceptions.ResourceNotFoundException;
import com.stocker.api.service.MovementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {
    private final MovementRepository movementRepository;
    private final CustomerRepository customerRepository;
    private final UserServiceImpl userService;
    private final ProductRepository productRepository;
    private final DefaultMapper<Movement, MovementRequest, MovementResponse> defaultMapper;


    @Override
    public void createMovement(MovementRequest request) {
        validateMovementRequest(request);

        User user = userService.getAuthenticatedUserOrElseThrow();
        Customer customer = getCustomerByIdOrElseThrow(request.customerId());

        calculateDiscountPercentage(customer);

        List<Product> products = validateAndProcessProducts(request.items());

        BigDecimal totalValue = calculateTotalMovementValue(products, request.items());

        Movement movement = Movement.builder()
                .customer(customer)
                .user(user)
                .movementType(request.movementType())
                .products(products)
                .totalValue(totalValue)
                .build();

        movementRepository.save(movement);
    }

    @Override
    public List<MovementResponse> getMovements() {
        return movementRepository.findAll().stream()
                .map(defaultMapper::toResponse)
                .toList();
    }

    @Override
    public MovementResponse getMovementById(UUID id) {
        return defaultMapper.toResponse(getMovementByIdOrElseThrow(id));
    }

    @Override
    public void updateMovement(UUID id, MovementRequest request) {
        Movement movement = getMovementByIdOrElseThrow(id);

        if (Objects.nonNull(request.customerId())) {
            Customer customer = getCustomerByIdOrElseThrow(request.customerId());
            movement.setCustomer(customer);
        }

        if (Objects.nonNull(request.movementType())) {
            if (Movement.MovementType.SALE.equals(request.movementType()) && Objects.isNull(request.customerId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must provide a customer id");
            }

            movement.setMovementType(request.movementType());
        }
    }

    @Override
    public void deleteMovement(UUID id) {
        Movement movement = getMovementByIdOrElseThrow(id);

        movementRepository.delete(movement);
    }

    public void calculateDiscountPercentage(Customer customer) {
        boolean isLongTermCustomer = customer.getCreationDate().isBefore(LocalDate.now().minusYears(1));

        int purchasesInLastSixMonths = customer.getPurchasesInLastSixMonths();

        if (isLongTermCustomer && purchasesInLastSixMonths >= 5) {
            customer.setDiscountPercentage(new BigDecimal("10.00"));
        } else if (isLongTermCustomer && purchasesInLastSixMonths >= 3) {
            customer.setDiscountPercentage(new BigDecimal("5.00"));
        } else {
            return;
        }

        customerRepository.save(customer);
    }


    private void validateMovementRequest(MovementRequest request) {
        if (request.movementType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must provide a movement type");
        }

        if (request.movementType().equals(Movement.MovementType.SALE) && request.customerId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must provide a customer id");
        }
    }

    private List<Product> validateAndProcessProducts(List<MovementItemDTO> items) {
        return items.stream()
                .map(item -> {
                    Product product = getProductByIdOrElseThrow(item.product());

                    if (product.getStockQuantity() < item.amount()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You do not have enough stock");
                    }

                    product.setStockQuantity(product.getStockQuantity() - item.amount());

                    return product;
                })
                .collect(Collectors.toList());
    }

    private BigDecimal calculateTotalMovementValue(List<Product> products, List<MovementItemDTO> items) {
        return items.stream()
                .map(item -> products.stream()
                        .filter(product -> product.getId().equals(item.product()))
                        .findFirst()
                        .map(product -> product.getPrice().multiply(new BigDecimal(item.amount())))
                        .orElse(BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Movement getMovementByIdOrElseThrow(UUID id) {
        return movementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movement not found"));
    }

    public Customer getCustomerByIdOrElseThrow(UUID id) {
        if (id == null) return null;

        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    public Product getProductByIdOrElseThrow(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }
}
