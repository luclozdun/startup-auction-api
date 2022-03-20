package com.auction.auction.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.security.dto.CustomerRequest;
import com.auction.auction.security.dto.CustomerResponse;
import com.auction.auction.security.dto.EmployeeRequest;
import com.auction.auction.security.dto.EmployeeResponse;
import com.auction.auction.security.model.Customer;
import com.auction.auction.security.model.Employee;
import com.auction.auction.security.repository.CustomerRepository;
import com.auction.auction.security.repository.EmployeeRepository;
import com.auction.auction.security.service.impl.AuthenticateServiceImpl;
import com.auction.auction.security.service.impl.RegisterServiceImpl;
import com.auction.auction.security.util.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.assertj.core.api.Assertions.*;

public class AuthenticateServiceTest {
    @Spy
    private CustomerRepository customerRepository;

    @Spy
    private EmployeeRepository employeeRespository;

    @Spy
    private BCryptPasswordEncoder encoder;

    @Spy
    private ModelMapper mapper;

    @Spy
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthenticateServiceImpl authenticateServiceImpl;

    @InjectMocks
    private RegisterServiceImpl registerServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("When Authenticate Customer Is Successful")
    void WhenSignUpCustomerIsSuccessful() {
        // Arrange
        CustomerRequest request = new CustomerRequest();
        request.setBrithday(new Date());
        request.setDni("dni");
        request.setEmail("email");
        request.setLastname("lastname");
        request.setName("name");
        request.setNumber("number");
        request.setPassword("password");
        request.setUsername("username");

        CustomerResponse response = registerServiceImpl.signupCustomer(request);
        Customer customer = mapper.map(response, Customer.class);

        when(customerRepository.getCustomerByUsername(Mockito.any())).thenReturn(Optional.of(customer));
        // Act
        String token = authenticateServiceImpl.authenticateCustomer(request.getUsername(), request.getPassword());
        // Assert
        assertNotNull(token);
    }

    @Test
    @DisplayName("When Authenticate Customer But Not Exist")
    void WhenSignUpCustomerButNotExist() {
        // Arrange
        when(customerRepository.getCustomerByUsername(Mockito.any())).thenReturn(Optional.empty());

        // Act}
        String message = "Customer not found by username";
        Throwable exception = catchThrowable(() -> {
            authenticateServiceImpl.authenticateCustomer("username", "password");
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Authenticate Customer But Credentials Invalid")
    void WhenSignUpCustomerButCredentialsInvalid() {
        // Arrange
        CustomerRequest request = new CustomerRequest();
        request.setBrithday(new Date());
        request.setDni("dni");
        request.setEmail("email");
        request.setLastname("lastname");
        request.setName("name");
        request.setNumber("number");
        request.setPassword("password");
        request.setUsername("username");

        CustomerResponse response = registerServiceImpl.signupCustomer(request);
        Customer customer = mapper.map(response, Customer.class);

        when(customerRepository.getCustomerByUsername(Mockito.any())).thenReturn(Optional.of(customer));
        // Act
        String message = "Credentials invalid";
        Throwable exception = catchThrowable(() -> {
            authenticateServiceImpl.authenticateCustomer(request.getUsername(), "pass");
        });
        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Authenticate Employee Is Successful")
    void WhenSignUpEmployeeIsSuccessful() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        request.setCode("code");
        ;
        request.setDni("dni");
        request.setEmail("email");
        request.setLastname("lastname");
        request.setName("name");
        request.setNumber("number");
        request.setPassword("password");
        request.setUsername("username");

        EmployeeResponse response = registerServiceImpl.signupEmployee(request);
        Employee employee = mapper.map(response, Employee.class);

        when(employeeRespository.getEmployeeByUsername(Mockito.any())).thenReturn(Optional.of(employee));
        // Act
        String token = authenticateServiceImpl.authenticateEmployee(request.getUsername(), request.getPassword());
        // Assert
        assertNotNull(token);
    }

    @Test
    @DisplayName("When Authenticate Employee But Not Exist")
    void WhenSignUpEmployeeButNotExist() {
        // Arrange
        when(employeeRespository.getEmployeeByUsername(Mockito.any())).thenReturn(Optional.empty());

        // Act}
        String message = "Employee not found by username";
        Throwable exception = catchThrowable(() -> {
            authenticateServiceImpl.authenticateEmployee("username", "password");
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Authenticate Employee But Credentials Invalid")
    void WhenSignUpEmployeeButCredentialsInvalid() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        request.setCode("code");
        request.setDni("dni");
        request.setEmail("email");
        request.setLastname("lastname");
        request.setName("name");
        request.setNumber("number");
        request.setPassword("password");
        request.setUsername("username");

        EmployeeResponse response = registerServiceImpl.signupEmployee(request);
        Employee employee = mapper.map(response, Employee.class);

        when(employeeRespository.getEmployeeByUsername(Mockito.any())).thenReturn(Optional.of(employee));
        // Act
        String message = "Credentials invalid";
        Throwable exception = catchThrowable(() -> {
            authenticateServiceImpl.authenticateEmployee(request.getUsername(), "pass");
        });
        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

}
