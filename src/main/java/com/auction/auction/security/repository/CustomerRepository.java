package com.auction.auction.security.repository;

import java.util.Optional;

import com.auction.auction.security.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> getCustomerById(Long id);

    Optional<Customer> getCustomerByUsernameAndPassword(String username, String password);

    Optional<Customer> getCustomerByUsername(String username);

    Optional<Customer> getCustomerByEmail(String email);

    Optional<Customer> getCustomerByDni(String dni);

    Optional<Customer> getCustomerByNumber(String number);
}
