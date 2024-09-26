package com.stocker.api.service.impl;

import com.stocker.api.config.security.IAuthenticationFacade;
import com.stocker.api.domain.dto.movement.MovementItemDTO;
import com.stocker.api.domain.dto.movement.MovementRequest;
import com.stocker.api.domain.dto.movement.MovementResponse;
import com.stocker.api.domain.entity.Customer;
import com.stocker.api.domain.entity.Movement;
import com.stocker.api.domain.entity.Product;
import com.stocker.api.domain.entity.User;
import com.stocker.api.domain.mapper.MovementMapper;
import com.stocker.api.domain.repository.CustomerRepository;
import com.stocker.api.domain.repository.MovementRepository;
import com.stocker.api.domain.repository.ProductRepository;
import com.stocker.api.domain.repository.UserRepository;
import com.stocker.api.service.MovementService;
import com.stocker.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
    private final MovementMapper movementMapper;


    @Override
    public void createMovement(MovementRequest request) {
        validateMovementRequest(request);

        User user = userService.getAuthenticatedUserOrElseThrow();
        Customer customer = request.customerId() != null ? getCustomerByIdOrElseThrow(request.customerId()) : null;

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
                .map(movementMapper::toResponse)
                .toList();
    }

    @Override
    public MovementResponse getMovementById(UUID id) {
        return movementMapper.toResponse(getMovementByIdOrElseThrow(id));
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

    public void calculateDiscountPercentage(Customer customer){
        boolean customerTime = customer.getCreationDate().isBefore(LocalDate.now().minusDays(365));
        BigDecimal purchaseBase = new BigDecimal(1000);

        boolean totalPurchaseValue = customer.getTotalPurchaseValue().compareTo(purchaseBase) > 0;

        if (customerTime && totalPurchaseValue) {

        }
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movement not found"));
    }

    public Customer getCustomerByIdOrElseThrow(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    public Product getProductByIdOrElseThrow(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }
}
