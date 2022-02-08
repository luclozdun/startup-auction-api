package com.auction.auction.security.service;

import com.auction.auction.security.dto.CustomerRequest;
import com.auction.auction.security.dto.CustomerResponse;
import com.auction.auction.security.dto.EmployeeRequest;
import com.auction.auction.security.dto.EmployeeResponse;

public interface RegisterService {
    EmployeeResponse signupEmployee(EmployeeRequest request);

    CustomerResponse signupCustomer(CustomerRequest request);
}
