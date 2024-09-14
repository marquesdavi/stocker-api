package com.stocker.api.domain.dto.customer;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomerRequest {
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private BigDecimal customerTime;
    private BigDecimal customerDiscount;

    // Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public BigDecimal getCustomerTime() {
        return customerTime;
    }

    public void setCustomerTime(BigDecimal customerTime) {
        this.customerTime = customerTime;
    }

    public BigDecimal getCustomerDiscount() {
        return customerDiscount;
    }

    public void setCustomerDiscount(BigDecimal customerDiscount) {
        this.customerDiscount = customerDiscount;
    }
}
