package com.auction.auction.security.service;

import java.util.List;

import com.auction.auction.security.dto.CustomerRequest;
import com.auction.auction.security.dto.CustomerResponse;

public interface CustomerService {
    List<CustomerResponse> getAll();

    CustomerResponse getById(Long id);

    CustomerResponse update(Long id, CustomerRequest request);

    void delete(Long id);

    Boolean getByUsername(String username);

    Boolean getByEmail(String email);

    Boolean getByNumber(String number);

    Boolean getByDni(String dni);
}
