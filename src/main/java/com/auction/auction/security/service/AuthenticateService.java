package com.auction.auction.security.service;

import com.auction.auction.security.dto.CustomerResponse;
import com.auction.auction.security.dto.EmployeeResponse;

public interface AuthenticateService {
    EmployeeResponse authenticateEmployee(String username, String password);

    CustomerResponse authenticateCustomer(String username, String password);
}
