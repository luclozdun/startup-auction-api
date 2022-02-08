package com.auction.auction.security.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.security.dto.EmployeeRequest;
import com.auction.auction.security.dto.EmployeeResponse;
import com.auction.auction.security.repository.EmployeeRespository;
import com.auction.auction.security.service.EmployeeService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRespository employeeRespository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<EmployeeResponse> getAll() {
        var employees = employeeRespository.findAll();
        var response = employees.stream().map(employee -> mapper.map(employee, EmployeeResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public EmployeeResponse getById(Long id) {
        var employee = employeeRespository.getEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Employee id not found"));
        var response = mapper.map(employee, EmployeeResponse.class);
        return response;
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        var employee = employeeRespository.getEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Employee id not found"));

        employee.setCode(request.getCode());
        employee.setDni(request.getDni());
        employee.setEmail(request.getEmail());
        employee.setLastname(request.getLastname());
        employee.setName(request.getName());
        employee.setNumber(request.getNumber());
        employee.setPassword(request.getPassword());
        employee.setUsername(request.getUsername());
        try {
            employeeRespository.save(employee);
            var response = mapper.map(employee, EmployeeResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating employee");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            employeeRespository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting employee");
        }
    }

    @Override
    public Boolean getByUsername(String username) {
        var employee = employeeRespository.getEmployeeByUsername(username).orElse(null);
        if (employee != null)
            return true;
        return false;
    }

    @Override
    public Boolean getByEmail(String email) {
        var employee = employeeRespository.getEmployeeByEmail(email).orElse(null);
        if (employee != null)
            return true;
        return false;
    }

    @Override
    public Boolean getByNumber(String number) {
        var employee = employeeRespository.getEmployeeByNumber(number).orElse(null);
        if (employee != null)
            return true;
        return false;
    }

    @Override
    public Boolean getByDni(String dni) {
        var employee = employeeRespository.getEmployeeByDni(dni).orElse(null);
        if (employee != null)
            return true;
        return false;
    }

}
