package com.auction.auction.security.service;

import com.auction.auction.security.dto.CustomerResponse;
import com.auction.auction.security.dto.EmployeeResponse;

public interface AuthenticateService {
    String authenticateEmployee(String username, String password);

    String authenticateCustomer(String username, String password);

    CustomerResponse authenticateCustomerFilter(String username);

    EmployeeResponse authenticateEmployeeFilter(String username);
}
