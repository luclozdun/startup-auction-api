package com.auction.auction.security.Controller;

import java.util.List;

import javax.validation.Valid;

import com.auction.auction.security.dto.EmployeeRequest;
import com.auction.auction.security.dto.EmployeeResponse;
import com.auction.auction.security.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    private ResponseEntity<List<EmployeeResponse>> getAll() {
        var employees = employeeService.getAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<EmployeeResponse> getById(@Valid @PathVariable(name = "id") Long id) {
        var employee = employeeService.getById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<EmployeeResponse> update(@Valid @PathVariable(name = "id") Long id,
            @Valid @RequestBody EmployeeRequest request) {
        var employee = employeeService.update(id, request);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@Valid @PathVariable(name = "id") Long id) {
        employeeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/email")
    private ResponseEntity<Boolean> getByEmail(@Valid @RequestBody EmployeeRequest request) {
        var exist = employeeService.getByEmail(request.getEmail());
        return new ResponseEntity<>(exist, HttpStatus.OK);
    }

    @GetMapping("/username")
    private ResponseEntity<Boolean> getByUsername(@Valid @RequestBody EmployeeRequest request) {
        var exist = employeeService.getByUsername(request.getUsername());
        return new ResponseEntity<>(exist, HttpStatus.OK);
    }

    @GetMapping("/number")
    private ResponseEntity<Boolean> getByNumber(@Valid @RequestBody EmployeeRequest request) {
        var exist = employeeService.getByNumber(request.getNumber());
        return new ResponseEntity<>(exist, HttpStatus.OK);
    }

    @GetMapping("/dni")
    private ResponseEntity<Boolean> getByDni(@Valid @RequestBody EmployeeRequest request) {
        var exist = employeeService.getByDni(request.getDni());
        return new ResponseEntity<>(exist, HttpStatus.OK);
    }

}
