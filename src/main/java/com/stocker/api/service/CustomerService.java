package com.stocker.api.service;

import com.stocker.api.domain.entity.Customer;

import java.util.List;

public interface CustomerService {
    void createCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(Customer customer);
    Customer findCustomerByEmail(String email);
    List<Customer> findAllCustomers();
}
