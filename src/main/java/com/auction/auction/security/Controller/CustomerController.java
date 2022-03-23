package com.auction.auction.security.controller;

import java.util.List;

import javax.validation.Valid;

import com.auction.auction.security.dto.CustomerRequest;
import com.auction.auction.security.dto.CustomerResponse;
import com.auction.auction.security.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    private ResponseEntity<List<CustomerResponse>> getAll() {
        var customers = customerService.getAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<CustomerResponse> getById(@Valid @PathVariable(name = "id") Long id) {
        var customer = customerService.getById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<CustomerResponse> update(@Valid @PathVariable(name = "id") Long id,
            @Valid @RequestBody CustomerRequest request) {
        var customer = customerService.update(id, request);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@Valid @PathVariable(name = "id") Long id) {
        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/email")
    private ResponseEntity<Boolean> getByEmail(@Valid @RequestBody CustomerRequest request) {
        var customer = customerService.getByEmail(request.getEmail());
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/username")
    private ResponseEntity<Boolean> getByUsername(@Valid @RequestBody CustomerRequest request) {
        var customer = customerService.getByUsername(request.getUsername());
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/number")
    private ResponseEntity<Boolean> getByNumber(@Valid @RequestBody CustomerRequest request) {
        var customer = customerService.getByNumber(request.getNumber());
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/dni")
    private ResponseEntity<Boolean> getByDni(@Valid @RequestBody CustomerRequest request) {
        var customer = customerService.getByDni(request.getDni());
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
