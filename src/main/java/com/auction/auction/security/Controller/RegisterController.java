package com.auction.auction.security.Controller;

import javax.validation.Valid;

import com.auction.auction.security.dto.CustomerRequest;
import com.auction.auction.security.dto.CustomerResponse;
import com.auction.auction.security.dto.EmployeeRequest;
import com.auction.auction.security.dto.EmployeeResponse;
import com.auction.auction.security.service.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registers")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/customer")
    private ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        var customer = registerService.signupCustomer(request);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/employee")
    private ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        var employee = registerService.signupEmployee(request);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}