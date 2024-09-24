package com.stocker.api.service;

import com.stocker.api.domain.entity.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    void createCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(Customer customer);
    Customer findCustomerById(UUID id);
    Customer findCustomerByCpf(String cpf);
    List<Customer> findAllCustomers();
}
