package com.stocker.api.service;

import com.stocker.api.domain.dto.customer.CustomerRequest;
import com.stocker.api.domain.dto.customer.CustomerResponse;
import com.stocker.api.domain.entity.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    void createCustomer(CustomerRequest customer);
    void updateCustomer(UUID id, CustomerRequest customer);
    void deleteCustomer(UUID id);
    CustomerResponse findCustomerById(UUID id);
    CustomerResponse findCustomerByCpf(String cpf);
    List<CustomerResponse> findAllCustomers();
}
