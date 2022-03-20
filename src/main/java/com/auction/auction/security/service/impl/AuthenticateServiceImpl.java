package com.auction.auction.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.security.dto.CustomerResponse;
import com.auction.auction.security.dto.EmployeeResponse;
import com.auction.auction.security.repository.CustomerRepository;
import com.auction.auction.security.repository.EmployeeRepository;
import com.auction.auction.security.service.AuthenticateService;
import com.auction.auction.security.util.JwtUtil;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRespository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String authenticateEmployee(String username, String password) {
        var employee = employeeRespository.getEmployeeByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Employee not found by username"));
        var passwordEncrypt = encoder.matches(password, employee.getPassword());
        if (!passwordEncrypt) {
            throw new ResourceNotFoundExceptionRequest("Credentials invalid");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("EMPLOYEE"));
        User user = new User(username, password, authorities);
        var token = jwtUtil.generateToken(user, "EMPLOYEE");

        return token;
    }

    @Override
    public String authenticateCustomer(String username, String password) {
        var customer = customerRepository.getCustomerByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found by username"));
        var passwordEncrypt = encoder.matches(password, customer.getPassword());
        if (!passwordEncrypt) {
            throw new ResourceNotFoundExceptionRequest("Credentials invalid");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("CUSTOMER"));
        User user = new User(username, password, authorities);
        String token = jwtUtil.generateToken(user, "CUSTOMER");

        return token;
    }

    @Override
    public CustomerResponse authenticateCustomerFilter(String username) {
        var customer = customerRepository.getCustomerByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found by username"));
        var resposne = mapper.map(customer, CustomerResponse.class);
        return resposne;
    }

    @Override
    public EmployeeResponse authenticateEmployeeFilter(String username) {
        var employee = employeeRespository.getEmployeeByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Employee not found by username"));
        var response = mapper.map(employee, EmployeeResponse.class);
        return response;
    }

}
