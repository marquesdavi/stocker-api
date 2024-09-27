package com.stocker.api.service.impl;

import com.stocker.api.domain.dto.customer.CustomerRequest;
import com.stocker.api.domain.dto.customer.CustomerResponse;
import com.stocker.api.domain.entity.Customer;
import com.stocker.api.domain.repository.CustomerRepository;
import com.stocker.api.domain.shared.DefaultMapper;
import com.stocker.api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final DefaultMapper<Customer, CustomerRequest, CustomerResponse> defaultMapper;

    @Override
    public void createCustomer(CustomerRequest request) {
        Customer customer = defaultMapper.toDomain(request);
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(UUID id, CustomerRequest request) {
        Customer existingCustomer = findCustomerByIdOrElseThrow(id);

        if (request.getName() != null) {
            existingCustomer.setName(request.getName());
        }
        if (request.getCpf() != null) {
            existingCustomer.setCpf(request.getCpf());
        }
        if (request.getBirthDate() != null) {
            existingCustomer.setBirthDate(request.getBirthDate());
        }
        if (request.getTotalPurchaseValue() != null) {
            existingCustomer.setTotalPurchaseValue(request.getTotalPurchaseValue());
        }
        if (request.getDiscountPercentage() != null) {
            existingCustomer.setDiscountPercentage(request.getDiscountPercentage());
        }

        customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(UUID id) {
        Customer customer = findCustomerByIdOrElseThrow(id);
        customerRepository.delete(customer);
    }

    @Override
    public CustomerResponse findCustomerById(UUID id) {
        Customer customer = findCustomerByIdOrElseThrow(id);
        return defaultMapper.toResponse(customer);
    }

    @Override
    public CustomerResponse findCustomerByCpf(String cpf) {
        Customer customer = customerRepository.findByCpf(cpf);
        return defaultMapper.toResponse(customer);
    }

    @Override
    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll().stream()
                .map(defaultMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Customer findCustomerByIdOrElseThrow(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
