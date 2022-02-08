package com.auction.auction.security.service.impl;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.security.dto.CustomerRequest;
import com.auction.auction.security.dto.CustomerResponse;
import com.auction.auction.security.dto.EmployeeRequest;
import com.auction.auction.security.dto.EmployeeResponse;
import com.auction.auction.security.model.Customer;
import com.auction.auction.security.model.Employee;
import com.auction.auction.security.repository.CustomerRepository;
import com.auction.auction.security.repository.EmployeeRespository;
import com.auction.auction.security.service.RegisterService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterImpl implements RegisterService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRespository employeeRespository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ModelMapper mapper;

    @Override
    public EmployeeResponse signupEmployee(EmployeeRequest request) {
        var employee = mapper.map(request, Employee.class);
        var paswordEncrypt = encoder.encode(request.getPassword());
        employee.setPassword(paswordEncrypt);
        try {
            employeeRespository.save(employee);
            var response = mapper.map(employee, EmployeeResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating employee");
        }
    }

    @Override
    public CustomerResponse signupCustomer(CustomerRequest request) {
        var customer = mapper.map(request, Customer.class);
        var passwordEncrypt = encoder.encode(request.getPassword());
        customer.setBonus(0L);
        customer.setWallet(0L);
        customer.setPassword(passwordEncrypt);
        try {
            customerRepository.save(customer);
            var response = mapper.map(customer, CustomerResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating customer");
        }
    }

}
