package com.auction.auction.security.service.impl;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.security.dto.CustomerResponse;
import com.auction.auction.security.dto.EmployeeResponse;
import com.auction.auction.security.repository.CustomerRepository;
import com.auction.auction.security.repository.EmployeeRespository;
import com.auction.auction.security.service.AuthenticateService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateImpl implements AuthenticateService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRespository employeeRespository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public EmployeeResponse authenticateEmployee(String username, String password) {
        var employee = employeeRespository.getEmployeeByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Employee not found by username"));
        var passwordEncrypt = encoder.matches(password, employee.getPassword());
        if (!passwordEncrypt) {
            throw new ResourceNotFoundExceptionRequest("Credentials invalid");
        }
        var response = mapper.map(employee, EmployeeResponse.class);
        return response;
    }

    @Override
    public CustomerResponse authenticateCustomer(String username, String password) {
        var customer = customerRepository.getCustomerByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found by username"));
        var passwordEncrypt = encoder.matches(password, customer.getPassword());
        if (!passwordEncrypt) {
            throw new ResourceNotFoundExceptionRequest("Credentials invalid");
        }
        var response = mapper.map(customer, CustomerResponse.class);
        return response;
    }

}
