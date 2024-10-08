package com.stocker.api.domain.repository;

import com.stocker.api.domain.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CustomerRepository extends MongoRepository<Customer, UUID> {
    Customer findByCpf(String cpf);
}
