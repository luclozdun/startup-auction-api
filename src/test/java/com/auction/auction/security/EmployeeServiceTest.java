package com.auction.auction.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.security.dto.EmployeeRequest;
import com.auction.auction.security.dto.EmployeeResponse;
import com.auction.auction.security.model.Employee;
import com.auction.auction.security.repository.EmployeeRepository;
import com.auction.auction.security.service.impl.EmployeeServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import static org.assertj.core.api.Assertions.*;

public class EmployeeServiceTest {

    @Spy
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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
    @DisplayName("When Find All Return Array List")
    void WhenFindAllReturnArrayList() {
        // Arrange
        Employee employee2 = new Employee();
        employee2.setCode("code");
        employee2.setDni("dni");
        employee2.setEmail("email");
        employee2.setId(2L);
        employee2.setLastname("lastname");
        employee2.setName("name");
        employee2.setNumber("number");
        employee2.setPassword("password");
        employee2.setUsername("username");

        ArrayList<Employee> employees = new ArrayList<Employee>();
        employees.add(employee);
        employees.add(employee2);
        when(employeeRepository.findAll()).thenReturn(employees);

        // Act
        List<EmployeeResponse> responses = employeeServiceImpl.getAll();

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());
    }

    @Test
    @DisplayName("When Find By Id Is Successful")
    void WhenFindByIdIsSuccessful() {
        // Arrange
        when(employeeRepository.getEmployeeById(1L)).thenReturn(Optional.of(employee));
        // Act
        EmployeeResponse response = employeeServiceImpl.getById(1L);
        // Assert
        assertNotNull(response);
        assertEquals("code", response.getCode());
        assertEquals("username", response.getUsername());
    }

    @Test
    @DisplayName("When Find By Id But Not Exist")
    void WhenFindByIdButNotExist() {
        // Arrange
        when(employeeRepository.getEmployeeById(1L)).thenReturn(Optional.empty());
        // Act
        String message = "Employee id not found";
        Throwable exception = catchThrowable(() -> {
            employeeServiceImpl.getById(1L);
        });
        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update Is Successful")
    void WhenUpdateIsSuccessful() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        request.setCode("code1");
        request.setDni("dni1");
        request.setEmail("email1");
        request.setLastname("lastname1");
        request.setName("name1");
        request.setNumber("number1");
        request.setPassword("password1");
        request.setUsername("username1");

        when(employeeRepository.save(Mockito.any())).thenReturn(employee);
        when(employeeRepository.getEmployeeById(1L)).thenReturn(Optional.of(employee));
        // Act
        EmployeeResponse response = employeeServiceImpl.update(1L, request);
        // Assert
        assertNotNull(response);
        assertEquals("code1", response.getCode());
        assertEquals("username1", response.getUsername());
    }

    @Test
    @DisplayName("When Update But Not Exist Employee")
    void WhenUpdateButNotExistEmployee() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        request.setCode("code1");
        request.setDni("dni1");
        request.setEmail("email1");
        request.setLastname("lastname1");
        request.setName("name1");
        request.setNumber("number1");
        request.setPassword("password1");
        request.setUsername("username1");

        when(employeeRepository.save(Mockito.any())).thenReturn(Optional.of(employee));
        when(employeeRepository.getEmployeeById(1L)).thenReturn(Optional.empty());
        // Act
        String message = "Employee id not found";
        Throwable exception = catchThrowable(() -> {
            employeeServiceImpl.update(1L, request);
        });
        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update But Error Ocurred While Updating")
    void WhenUpdateButErrorOcurredWhileUpdating() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        request.setCode("code1");
        request.setDni("dni1");
        request.setEmail("email1");
        request.setLastname("lastname1");
        request.setName("name1");
        request.setNumber("number1");
        request.setPassword("password1");
        request.setUsername("username1");

        when(employeeRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        when(employeeRepository.getEmployeeById(1L)).thenReturn(Optional.of(employee));
        // Act
        String message = "Error ocurred while updating employee";
        Throwable exception = catchThrowable(() -> {
            employeeServiceImpl.update(1L, request);
        });
        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete Is Successful")
    void WhenDeleteIsSuccessful() {
        // Arrange
        doNothing().when(employeeRepository).deleteById(1L);
        // Act
        Throwable exception = catchThrowable(() -> {
            employeeServiceImpl.delete(1L);
        });
        // Assert
        assertNull(exception);
    }

    @Test
    @DisplayName("When Delete But Error Ocurred While Deleting")
    void WhenDeleteButErrorOcurredWhileDeleting() {
        // Arrange
        doThrow(ResourceNotFoundExceptionRequest.class).when(employeeRepository).deleteById(1L);
        // Act
        String message = "Error ocurred while deleting employee";
        Throwable exception = catchThrowable(() -> {
            employeeServiceImpl.delete(1L);
        });
        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Get By Username Return True")
    void WhenGetByUsernameReturnTrue() {
        // Arrange
        when(employeeRepository.getEmployeeByUsername("username")).thenReturn(Optional.of(employee));
        // Act
        Boolean valid = employeeServiceImpl.getByUsername("username");
        // Assert
        assertEquals(true, valid);
    }

    @Test
    @DisplayName("When Get By Username Return False")
    void WhenGetByUsernameReturnFalse() {
        // Arrange
        when(employeeRepository.getEmployeeByUsername("username")).thenReturn(Optional.empty());
        // Act
        Boolean valid = employeeServiceImpl.getByUsername("username");
        // Assert
        assertEquals(false, valid);
    }

    @Test
    @DisplayName("When Get By Email Return True")
    void WhenGetByEmailReturnTrue() {
        // Arrange
        when(employeeRepository.getEmployeeByEmail("username@hotmail.com")).thenReturn(Optional.of(employee));
        // Act
        Boolean valid = employeeServiceImpl.getByEmail("username@hotmail.com");
        // Assert
        assertEquals(true, valid);
    }

    @Test
    @DisplayName("When Get By Email Return False")
    void WhenGetByEmailReturnFalse() {
        // Arrange
        when(employeeRepository.getEmployeeByEmail("username@hotmail.com")).thenReturn(Optional.empty());
        // Act
        Boolean valid = employeeServiceImpl.getByEmail("username@hotmail.com");
        // Assert
        assertEquals(false, valid);
    }

    @Test
    @DisplayName("When Get By Number Return True")
    void WhenGetByNumberReturnTrue() {
        // Arrange
        when(employeeRepository.getEmployeeByNumber("789456123")).thenReturn(Optional.of(employee));
        // Act
        Boolean valid = employeeServiceImpl.getByNumber("789456123");
        // Assert
        assertEquals(true, valid);
    }

    @Test
    @DisplayName("When Get By Number Return False")
    void WhenGetByNumberReturnFalse() {
        // Arrange
        when(employeeRepository.getEmployeeByNumber("789456123")).thenReturn(Optional.empty());
        // Act
        Boolean valid = employeeServiceImpl.getByNumber("789456123");
        // Assert
        assertEquals(false, valid);
    }

    @Test
    @DisplayName("When Get By Dni Return True")
    void WhenGetByDniReturnTrue() {
        // Arrange
        when(employeeRepository.getEmployeeByDni("1111111")).thenReturn(Optional.of(employee));
        // Act
        Boolean valid = employeeServiceImpl.getByDni("1111111");
        // Assert
        assertEquals(true, valid);
    }

    @Test
    @DisplayName("When Get By Number Return False")
    void WhenGetByDniReturnFalse() {
        // Arrange
        when(employeeRepository.getEmployeeByDni("1111111")).thenReturn(Optional.empty());
        // Act
        Boolean valid = employeeServiceImpl.getByDni("1111111");
        // Assert
        assertEquals(false, valid);
    }

}
