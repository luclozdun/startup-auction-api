package com.auction.auction.security.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.security.dto.CustomerRequest;
import com.auction.auction.security.dto.CustomerResponse;
import com.auction.auction.security.repository.CustomerRepository;
import com.auction.auction.security.service.CustomerService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<CustomerResponse> getAll() {
        var customers = customerRepository.findAll();
        var response = customers.stream().map(customer -> mapper.map(customer, CustomerResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public CustomerResponse getById(Long id) {
        var customer = customerRepository.getCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found by id"));
        var response = mapper.map(customer, CustomerResponse.class);
        return response;
    }

    @Override
    public CustomerResponse update(Long id, CustomerRequest request) {
        var customer = customerRepository.getCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found by id"));

        customer.setBrithday(request.getBrithday());
        customer.setDni(request.getDni());
        customer.setEmail(request.getEmail());
        customer.setLastname(request.getLastname());
        customer.setName(request.getName());
        customer.setNumber(request.getNumber());
        customer.setPassword(request.getPassword());
        customer.setUsername(request.getUsername());
        try {
            customerRepository.save(customer);
            var response = mapper.map(customer, CustomerResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating customer");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting customer");
        }
    }

    @Override
    public Boolean getByUsername(String username) {
        var customer = customerRepository.getCustomerByUsername(username)
                .orElse(null);
        if (customer != null)
            return true;
        return false;
    }

    @Override
    public Boolean getByEmail(String email) {
        var customer = customerRepository.getCustomerByEmail(email)
                .orElse(null);
        if (customer != null)
            return true;
        return false;
    }

    @Override
    public Boolean getByNumber(String number) {
        var customer = customerRepository.getCustomerByNumber(number)
                .orElse(null);
        if (customer != null)
            return true;
        return false;
    }

    @Override
    public Boolean getByDni(String dni) {
        var customer = customerRepository.getCustomerByDni(dni)
                .orElse(null);
        if (customer != null)
            return true;
        return false;
    }

}
