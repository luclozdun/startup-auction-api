package com.auction.auction.security.Controller;

import javax.validation.Valid;

import com.auction.auction.security.dto.AuthenticateRequest;
import com.auction.auction.security.dto.CustomerResponse;
import com.auction.auction.security.dto.EmployeeResponse;
import com.auction.auction.security.service.AuthenticateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/authenticate")
public class AuthenticateController {

    @Autowired
    private AuthenticateService authenticateService;

    @PostMapping("/customer")
    private ResponseEntity<CustomerResponse> authenticateCustomer(@Valid @RequestBody AuthenticateRequest request) {
        var customer = authenticateService.authenticateCustomer(request.getUsername(), request.getPassword());
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/employee")
    private ResponseEntity<EmployeeResponse> authenticateEmployee(@Valid @RequestBody AuthenticateRequest request) {
        var employee = authenticateService.authenticateEmployee(request.getUsername(), request.getPassword());
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
