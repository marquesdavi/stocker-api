package com.stocker.api.domain.mapper;

import com.stocker.api.domain.dto.customer.CustomerRequest;
import com.stocker.api.domain.dto.customer.CustomerResponse;
import com.stocker.api.domain.entity.Customer;
import com.stocker.api.domain.shared.DefaultMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerMapper implements DefaultMapper<Customer, CustomerRequest, CustomerResponse> {

    @Override
    public Customer toDomain(CustomerRequest request) {
        return Customer.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .cpf(request.getCpf())
                .birthDate(request.getBirthDate())
                .totalPurchaseValue(request.getTotalPurchaseValue())
                .discountPercentage(request.getDiscountPercentage())
                .build();
    }

    @Override
    public CustomerRequest toRequest(Customer customer) {
        return CustomerRequest.builder()
                .name(customer.getName())
                .cpf(customer.getCpf())
                .birthDate(customer.getBirthDate())
                .totalPurchaseValue(customer.getTotalPurchaseValue())
                .discountPercentage(customer.getDiscountPercentage())
                .build();
    }

    @Override
    public CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .cpf(customer.getCpf())
                .birthDate(customer.getBirthDate())
                .creationDate(customer.getCreationDate())
                .totalPurchaseValue(customer.getTotalPurchaseValue())
                .discountPercentage(customer.getDiscountPercentage())
                .build();
    }
}
