package com.auction.auction.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.security.dto.CustomerRequest;
import com.auction.auction.security.dto.CustomerResponse;
import com.auction.auction.security.model.Customer;
import com.auction.auction.security.repository.CustomerRepository;
import com.auction.auction.security.service.impl.CustomerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.*;

public class CustomerServiceTest {

    @Spy
    private CustomerRepository customerRepository;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    private Customer customer;

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
    }

    @Test
    @DisplayName("When Find All Customer Return Array List")
    void WhenFindAllCustomerReturnArrayList() {
        // Arrange
        Customer customer2 = new Customer();
        customer2.setBonus(0L);
        customer2.setBrithday(new Date());
        customer2.setDni("dni");
        customer2.setEmail("email");
        customer2.setId(2L);
        customer2.setLastname("lastname");
        customer2.setName("name");
        customer2.setPassword("password");
        customer2.setUsername("username");
        customer2.setWallet(15L);

        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(customer);
        customers.add(customer2);
        when(customerRepository.findAll()).thenReturn(customers);

        // Act
        List<CustomerResponse> response = customerServiceImpl.getAll();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    @DisplayName("When Find By Id Is Successful")
    void WhenFindByIdIsSuccessful() {
        // Arrange
        when(customerRepository.getCustomerById(1L)).thenReturn(Optional.of(customer));
        // Act
        CustomerResponse response = customerServiceImpl.getById(1L);
        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("username", response.getUsername());
    }

    @Test
    @DisplayName("When Find By Id But Not Exist")
    void WhenFindByIdButNotExist() {
        // Arrange
        when(customerRepository.getCustomerById(1L)).thenReturn(Optional.empty());
        // Act
        String message = "Customer not found by id";
        Throwable exception = catchThrowable(() -> {
            customerServiceImpl.getById(1L);
        });
        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update Is Successful")
    void WhenUpdateIsSuccessful() {
        // Arrange
        CustomerRequest request = new CustomerRequest();
        request.setBrithday(new Date());
        request.setDni("dni2");
        request.setEmail("email2");
        request.setLastname("lastname2");
        request.setName("name2");
        request.setNumber("number2");
        request.setPassword("password2");
        request.setUsername("username2");

        when(customerRepository.save(Mockito.any())).thenReturn(customer);
        when(customerRepository.getCustomerById(1L)).thenReturn(Optional.of(customer));
        // Act
        CustomerResponse response = customerServiceImpl.update(1L, request);
        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("username2", response.getUsername());
    }

    @Test
    @DisplayName("When Update But Not Exist Customer")
    void WhenUpdateButNotExistCustomer() {
        // Arrange
        CustomerRequest request = new CustomerRequest();
        request.setBrithday(new Date());
        request.setDni("dni2");
        request.setEmail("email2");
        request.setLastname("lastname2");
        request.setName("name2");
        request.setNumber("number2");
        request.setPassword("password2");
        request.setUsername("username2");

        when(customerRepository.save(Mockito.any())).thenReturn(customer);
        when(customerRepository.getCustomerById(1L)).thenReturn(Optional.empty());
        // Act
        String message = "Customer not found by id";
        Throwable exception = catchThrowable(() -> {
            customerServiceImpl.update(1L, request);
        });
        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update But Error Ocurred While Updating")
    void WhenUpdateButErrorOcurredWhileUpdating() {
        // Arrange
        CustomerRequest request = new CustomerRequest();
        request.setBrithday(new Date());
        request.setDni("dni2");
        request.setEmail("email2");
        request.setLastname("lastname2");
        request.setName("name2");
        request.setNumber("number2");
        request.setPassword("password2");
        request.setUsername("username2");

        when(customerRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        when(customerRepository.getCustomerById(1L)).thenReturn(Optional.of(customer));
        // Act
        String message = "Error ocurred while updating customer";
        Throwable exception = catchThrowable(() -> {
            customerServiceImpl.update(1L, request);
        });
        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete Is Success")
    void WhenDeleteIsSuccess() {
        // Arrange
        doNothing().when(customerRepository).deleteById(1L);
        // Act
        Throwable exception = catchThrowable(() -> {
            customerServiceImpl.delete(1L);
        });
        // Assert
        assertNull(exception);
    }

    @Test
    @DisplayName("When Delete But Error Ocurred While Deleting")
    void WhenDeleteButErrorOcurredWhileDeleting() {
        // Arrange
        doThrow(ResourceNotFoundExceptionRequest.class).when(customerRepository).deleteById(1L);
        // Act
        String message = "Error ocurred while deleting customer";
        Throwable exception = catchThrowable(() -> {
            customerServiceImpl.delete(1L);
        });
        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Get By Username Return True")
    void WhenGetByUsernameReturnTrue() {
        // Arrange
        when(customerRepository.getCustomerByUsername("username")).thenReturn(Optional.of(customer));
        // Act
        Boolean valid = customerServiceImpl.getByUsername("username");
        // Assert
        assertEquals(true, valid);
    }

    @Test
    @DisplayName("When Get By Username Return False")
    void WhenGetByUsernameReturnFalse() {
        // Arrange
        when(customerRepository.getCustomerByUsername("username")).thenReturn(Optional.empty());
        // Act
        Boolean valid = customerServiceImpl.getByUsername("username");
        // Assert
        assertEquals(false, valid);
    }

    @Test
    @DisplayName("When Get By Email Return True")
    void WhenGetByEmailReturnTrue() {
        // Arrange
        when(customerRepository.getCustomerByEmail("username@hotmail.com")).thenReturn(Optional.of(customer));
        // Act
        Boolean valid = customerServiceImpl.getByEmail("username@hotmail.com");
        // Assert
        assertEquals(true, valid);
    }

    @Test
    @DisplayName("When Get By Email Return False")
    void WhenGetByEmailReturnFalse() {
        // Arrange
        when(customerRepository.getCustomerByEmail("username@hotmail.com")).thenReturn(Optional.empty());
        // Act
        Boolean valid = customerServiceImpl.getByEmail("username@hotmail.com");
        // Assert
        assertEquals(false, valid);
    }

    @Test
    @DisplayName("When Get By Number Return True")
    void WhenGetByNumberReturnTrue() {
        // Arrange
        when(customerRepository.getCustomerByNumber("789456123")).thenReturn(Optional.of(customer));
        // Act
        Boolean valid = customerServiceImpl.getByNumber("789456123");
        // Assert
        assertEquals(true, valid);
    }

    @Test
    @DisplayName("When Get By Number Return False")
    void WhenGetByNumberReturnFalse() {
        // Arrange
        when(customerRepository.getCustomerByNumber("789456123")).thenReturn(Optional.empty());
        // Act
        Boolean valid = customerServiceImpl.getByNumber("789456123");
        // Assert
        assertEquals(false, valid);
    }

    @Test
    @DisplayName("When Get By Dni Return True")
    void WhenGetByDniReturnTrue() {
        // Arrange
        when(customerRepository.getCustomerByDni("1111111")).thenReturn(Optional.of(customer));
        // Act
        Boolean valid = customerServiceImpl.getByDni("1111111");
        // Assert
        assertEquals(true, valid);
    }

    @Test
    @DisplayName("When Get By Number Return False")
    void WhenGetByDniReturnFalse() {
        // Arrange
        when(customerRepository.getCustomerByDni("1111111")).thenReturn(Optional.empty());
        // Act
        Boolean valid = customerServiceImpl.getByDni("1111111");
        // Assert
        assertEquals(false, valid);
    }

}
