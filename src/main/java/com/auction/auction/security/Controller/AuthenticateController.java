package com.auction.auction.security.Controller;

import javax.validation.Valid;

import com.auction.auction.security.dto.AuthenticateRequest;
import com.auction.auction.security.service.AuthenticateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticates")
public class AuthenticateController {

    @Autowired
    private AuthenticateService authenticateService;

    @PostMapping("/customer")
    private ResponseEntity<String> authenticateCustomer(@Valid @RequestBody AuthenticateRequest request) {
        var token = authenticateService.authenticateCustomer(request.getUsername(), request.getPassword());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/employee")
    private ResponseEntity<String> authenticateEmployee(@Valid @RequestBody AuthenticateRequest request) {
        var token = authenticateService.authenticateEmployee(request.getUsername(), request.getPassword());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
