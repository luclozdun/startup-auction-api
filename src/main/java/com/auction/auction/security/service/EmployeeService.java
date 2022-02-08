package com.auction.auction.security.service;

import java.util.List;

import com.auction.auction.security.dto.EmployeeRequest;
import com.auction.auction.security.dto.EmployeeResponse;

public interface EmployeeService {
    List<EmployeeResponse> getAll();

    EmployeeResponse getById(Long id);

    EmployeeResponse update(Long id, EmployeeRequest request);

    void delete(Long id);

    Boolean getByUsername(String username);

    Boolean getByEmail(String email);

    Boolean getByNumber(String number);

    Boolean getByDni(String dni);
}
