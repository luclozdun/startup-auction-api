package com.auction.auction.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.security.dto.CustomerRequest;
import com.auction.auction.security.dto.CustomerResponse;
import com.auction.auction.security.dto.EmployeeRequest;
import com.auction.auction.security.dto.EmployeeResponse;
import com.auction.auction.security.model.Customer;
import com.auction.auction.security.model.Employee;
import com.auction.auction.security.repository.CustomerRepository;
import com.auction.auction.security.repository.EmployeeRepository;
import com.auction.auction.security.service.impl.RegisterServiceImpl;

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

public class RegisterServiceTest {

    @Spy
    private CustomerRepository customerRepository;

    @Spy
    private EmployeeRepository employeeRespository;

    @Spy
    private BCryptPasswordEncoder encoder;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private RegisterServiceImpl registerServiceImpl;

    private Customer customer;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setBonus(0L);
        customer.setBrithday(new Date());
        customer.setDni("dni");
        customer.setEmail("email");
        customer.setId(1L);
        customer.setLastname("lastname");
        customer.setName("name");
        customer.setPassword("password");
        customer.setUsername("username");
        customer.setWallet(15L);

        employee = new Employee();
        employee.setCode("code");
        employee.setDni("dni");
        employee.setEmail("email");
        employee.setId(1L);
        employee.setLastname("lastname");
        employee.setName("name");
        employee.setNumber("number");
        employee.setPassword("password");
        employee.setUsername("username");
    }

    @Test
    @DisplayName("When SignUp Customer Is Successful")
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

        when(customerRepository.save(Mockito.any())).thenReturn(customer);
        // Act
        CustomerResponse response = registerServiceImpl.signupCustomer(request);
        Boolean passwordValid = encoder.matches("password", response.getPassword());

        // Assert
        assertEquals(true, passwordValid);
        assertEquals("username", request.getUsername());
        assertEquals("dni", request.getDni());
    }

    @Test
    @DisplayName("When SignUp Customer But Error Ocurred While Saving")
    void WhenSignUpCustomerButErrorOcurredWhileSaving() {
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

        when(customerRepository.save(Mockito.any())).thenThrow(ResourceNotFoundExceptionRequest.class);
        // Act
        String message = "Error ocurred while creating customer";
        Throwable exception = catchThrowable(() -> {
            registerServiceImpl.signupCustomer(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When SignUp Employee Is Successful")
    void WhenSignUpEmployeeIsSuccessful() {
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

        when(employeeRespository.save(Mockito.any())).thenReturn(employee);
        // Act
        EmployeeResponse response = registerServiceImpl.signupEmployee(request);
        Boolean passwordValid = encoder.matches("password", response.getPassword());

        // Assert
        assertEquals(true, passwordValid);
        assertEquals("username", request.getUsername());
        assertEquals("dni", request.getDni());
    }

    @Test
    @DisplayName("When SignUp Employee But Error Ocurred While Saving")
    void WhenSignUpEmployeeButErrorOcurredWhileSaving() {
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

        when(employeeRespository.save(Mockito.any())).thenThrow(ResourceNotFoundExceptionRequest.class);
        // Act
        String message = "Error ocurred while creating employee";
        Throwable exception = catchThrowable(() -> {
            registerServiceImpl.signupEmployee(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }
}
