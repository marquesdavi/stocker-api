package com.stocker.api.controller;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stocker.api.domain.entity.Customer;
import com.stocker.api.domain.dto.customer.CustomerRequest;
import com.stocker.api.domain.dto.customer.CustomerResponse;
import com.stocker.api.service.CustomerService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Endpoint para cadastrar cliente
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {

        Customer customer = convertToEntity(customerRequest);
        customerService.createCustomer(customer);

        CustomerResponse response = convertToResponse(customer);
        return ResponseEntity.ok(response);
    }

    // Endpoint para obter cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable UUID id) {
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        CustomerResponse response = convertToResponse(customer);
        return ResponseEntity.ok(response);
    }

    // Endpoint para obter cliente por CPF
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<CustomerResponse> getCustomerByCpf(@PathVariable String cpf) {
        Customer customer = customerService.findCustomerByCpf(cpf);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        CustomerResponse response = convertToResponse(customer);
        return ResponseEntity.ok(response);

    }

    // Endpoint para atualizar dados do cliente
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable UUID id, @RequestBody CustomerRequest customerRequest) {
        Customer customer = convertToEntity(customerRequest);
        customer.setId(id);
        customerService.updateCustomer(customer);
        CustomerResponse response = convertToResponse(customer);
        return ResponseEntity.ok(response);
    }

    // Endpoint para remover cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        customerService.deleteCustomer(customer);
        return ResponseEntity.noContent().build();
    }

    // Métodos auxiliares para conversão entre DTO e entidade

    private Customer convertToEntity(CustomerRequest request) {
        if (request == null) {
            return null;
        }
    
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setCpf(request.getCpf());
        customer.setBirthDate(request.getBirthDate());
        customer.setCustomerTime(request.getCustomerTime());
        customer.setCustomerDiscount(request.getCustomerDiscount());
    
    
        return customer;
    }
    
    private CustomerResponse convertToResponse(Customer customer) {
        if (customer == null) {
            return null;
        }
    
        CustomerResponse response = new CustomerResponse();
        response.setName(customer.getName());
        response.setCpf(customer.getCpf());
        response.setBirthDate(customer.getBirthDate());
        response.setCustomerTime(customer.getCustomerTime());
        response.setCustomerDiscount(customer.getCustomerDiscount());
 
    
        return response;
    }
}
