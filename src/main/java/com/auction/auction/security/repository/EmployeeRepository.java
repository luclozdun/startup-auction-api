package com.auction.auction.security.repository;

import java.util.Optional;

import com.auction.auction.security.model.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> getEmployeeById(Long id);

    Optional<Employee> getEmployeeByUsernameAndPassword(String username, String password);

    Optional<Employee> getEmployeeByUsername(String username);

    Optional<Employee> getEmployeeByEmail(String email);

    Optional<Employee> getEmployeeByNumber(String number);

    Optional<Employee> getEmployeeByDni(String dni);
}
