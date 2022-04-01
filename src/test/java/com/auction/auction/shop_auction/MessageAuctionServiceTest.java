package com.auction.auction.shop_auction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.product.model.Category;
import com.auction.auction.security.dto.CustomerResponse;
import com.auction.auction.security.model.Customer;
import com.auction.auction.security.repository.CustomerRepository;
import com.auction.auction.security.service.impl.CustomerServiceImpl;
import com.auction.auction.shop_auction.dto.MessageAuctionRequest;
import com.auction.auction.shop_auction.dto.MessageAuctionResponse;
import com.auction.auction.shop_auction.model.Auction;
import com.auction.auction.shop_auction.model.MessageAuction;
import com.auction.auction.shop_auction.repository.AuctionRepository;
import com.auction.auction.shop_auction.repository.MessageAuctionRepository;
import com.auction.auction.shop_auction.service.impl.MessageAuctionServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.*;

public class MessageAuctionServiceTest {

    @Spy
    private MessageAuctionRepository messageAuctionRepository;

    @Spy
    private AuctionRepository auctionRepository;

    @Spy
    private CustomerRepository customerRepository;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private MessageAuctionServiceImpl messageAuctionServiceImpl;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    private Auction auction;

    private Auction auction2Customer;

    private Customer customer;

    private MessageAuction messageAuction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        auction = new Auction();
        auction.setAvaible(true);
        auction.setCaracteristic("It's a black computer");
        auction.setCategory(new Category());
        auction.setId(1L);
        auction.setImage1("Image 1");
        auction.setImage2("Image 2");
        auction.setImage3("Image 3");
        auction.setPriceBase(1550.00);
        auction.setName("Computer");
        auction.setPrice(150.00);
        auction.setCustomer(null);
        auction.setVideo("Video");

        customer = new Customer();
        customer.setId(1L);
        customer.setEmail("email@hotmail");
        customer.setDni("78945612");
        customer.setWallet(1000.0);

        messageAuction = new MessageAuction();
        messageAuction.setAuction(auction);
        messageAuction.setCustomer(customer);
        messageAuction.setId(1L);
        messageAuction.setPrice(150.0);

        auction2Customer = new Auction();
        auction2Customer.setAvaible(true);
        auction2Customer.setCaracteristic("It's a black computer");
        auction2Customer.setCategory(new Category());
        auction2Customer.setId(1L);
        auction2Customer.setImage1("Image 1");
        auction2Customer.setImage2("Image 2");
        auction2Customer.setImage3("Image 3");
        auction2Customer.setPriceBase(150.00);
        auction2Customer.setName("Computer");
        auction2Customer.setPrice(150.00);
        auction2Customer.setCustomer(customer);
        auction2Customer.setVideo("Video");
    }

    @Test
    @DisplayName("When Find All Return Array Message")
    void WhenFindAllReturnArrayMessage() {
        // Arrange
        MessageAuction newMessage = new MessageAuction();
        newMessage.setAuction(auction);
        newMessage.setCustomer(customer);
        newMessage.setId(2L);
        newMessage.setPrice(160.0);

        ArrayList<MessageAuction> messages = new ArrayList<MessageAuction>();
        messages.add(newMessage);
        messages.add(messageAuction);

        when(messageAuctionRepository.findAll()).thenReturn(messages);
        // Act
        List<MessageAuctionResponse> response = messageAuctionServiceImpl.getAll();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    @DisplayName("When Find By Auction Id Return Array Message")
    void WhenFindByAuctionIdReturnArrayMessage() {
        // Arrange
        MessageAuction newMessage = new MessageAuction();
        newMessage.setAuction(auction);
        newMessage.setCustomer(customer);
        newMessage.setId(2L);
        newMessage.setPrice(160.0);

        ArrayList<MessageAuction> messages = new ArrayList<MessageAuction>();
        messages.add(newMessage);
        messages.add(messageAuction);

        when(messageAuctionRepository.getMessageAuctionsByAuctionId(1L)).thenReturn(messages);
        // Act
        List<MessageAuctionResponse> response = messageAuctionServiceImpl.getMessagesByAuctionId(1L);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    @DisplayName("When Find By Id Is Successful")
    void WhenFindByIdIsSuccessful() {
        // Arrange
        when(messageAuctionRepository.getMessageAuctionById(1L)).thenReturn(Optional.of(messageAuction));
        // Act
        MessageAuctionResponse response = messageAuctionServiceImpl.getById(1L);
        // Assert
        assertEquals(1L, response.getId());
        assertEquals(150.0, response.getPrice());
    }

    @Test
    @DisplayName("When Find By Id Return Message Not Found")
    void WhenFindByIdReturnMessageNotFound() {
        // Arrange
        when(messageAuctionRepository.getMessageAuctionById(2L)).thenReturn(Optional.empty());
        // Act
        String message = "Message not found";
        Throwable exception = catchThrowable(() -> {
            messageAuctionServiceImpl.getById(2L);
        });
        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create Message Is Successful")
    void WhenCreateMessageIsSuccessful() {
        // Arrange
        MessageAuctionRequest request = new MessageAuctionRequest();
        request.setAuctionId(1L);
        request.setCustomerId(1L);
        request.setPrice(200.0);

        when(auctionRepository.getAuctionById(1L)).thenReturn(Optional.of(auction));
        when(customerRepository.getCustomerById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(customer));
        when(customerRepository.save(Mockito.any())).thenReturn(customer);
        when(messageAuctionRepository.save(Mockito.any())).thenReturn(messageAuction);
        when(auctionRepository.save(Mockito.any())).thenReturn(auction);
        // Act
        MessageAuctionResponse response = messageAuctionServiceImpl.create(request);
        CustomerResponse customerResponse = customerServiceImpl.getById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(800, customerResponse.getWallet());
    }

    @Test
    @DisplayName("When Create Message But Invalid Balance")
    void WhenCreateMessageButInvalidBalance() {
        // Arrange
        MessageAuctionRequest request = new MessageAuctionRequest();
        request.setAuctionId(1L);
        request.setCustomerId(1L);
        request.setPrice(10.0);

        when(auctionRepository.getAuctionById(1L)).thenReturn(Optional.of(auction));
        when(customerRepository.getCustomerById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(customer));
        when(customerRepository.save(Mockito.any())).thenReturn(customer);
        when(messageAuctionRepository.save(Mockito.any())).thenReturn(messageAuction);
        when(auctionRepository.save(Mockito.any())).thenReturn(auction);
        // Act
        String message = "Invalid balance";
        Throwable exception = catchThrowable(() -> {
            messageAuctionServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create Message But Insufficient Balance")
    void WhenCreateMessageButInsufficientBalance() {
        // Arrange
        customer.setWallet(100.0);
        MessageAuctionRequest request = new MessageAuctionRequest();
        request.setAuctionId(1L);
        request.setCustomerId(1L);
        request.setPrice(800.0);

        when(auctionRepository.getAuctionById(1L)).thenReturn(Optional.of(auction));
        when(customerRepository.getCustomerById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(customer));
        when(customerRepository.save(Mockito.any())).thenReturn(customer);
        when(messageAuctionRepository.save(Mockito.any())).thenReturn(messageAuction);
        when(auctionRepository.save(Mockito.any())).thenReturn(auction);
        // Act
        String message = "Insufficient balance";
        Throwable exception = catchThrowable(() -> {
            messageAuctionServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create Message But Customer Not Found")
    void WhenCreateMessageButCustomerNotFound() {
        // Arrange
        MessageAuctionRequest request = new MessageAuctionRequest();
        request.setAuctionId(1L);
        request.setCustomerId(1L);
        request.setPrice(200.0);

        when(auctionRepository.getAuctionById(1L)).thenReturn(Optional.of(auction));
        when(customerRepository.getCustomerById(1L)).thenReturn(Optional.empty());
        when(customerRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(customer));
        when(customerRepository.save(Mockito.any())).thenReturn(customer);
        when(messageAuctionRepository.save(Mockito.any())).thenReturn(messageAuction);
        when(auctionRepository.save(Mockito.any())).thenReturn(auction);
        // Act
        String message = "Customer not found";
        Throwable exception = catchThrowable(() -> {
            messageAuctionServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create Message But Auction Not Found")
    void WhenCreateMessageButAuctionNotFound() {
        // Arrange
        MessageAuctionRequest request = new MessageAuctionRequest();
        request.setAuctionId(1L);
        request.setCustomerId(1L);
        request.setPrice(200.0);

        when(auctionRepository.getAuctionById(1L)).thenReturn(Optional.empty());
        when(customerRepository.getCustomerById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(customer));
        when(customerRepository.save(Mockito.any())).thenReturn(customer);
        when(messageAuctionRepository.save(Mockito.any())).thenReturn(messageAuction);
        when(auctionRepository.save(Mockito.any())).thenReturn(auction);
        // Act
        String message = "Auction not found";
        Throwable exception = catchThrowable(() -> {
            messageAuctionServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create Message But Error Ocurred While Updating A Customer")
    void WhenCreateMessageButErrorOcurredWhileUpdatingACustomer() {
        // Arrange
        MessageAuctionRequest request = new MessageAuctionRequest();
        request.setAuctionId(1L);
        request.setCustomerId(1L);
        request.setPrice(200.0);

        when(auctionRepository.getAuctionById(1L)).thenReturn(Optional.of(auction));
        when(customerRepository.getCustomerById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(customer));
        when(customerRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        when(messageAuctionRepository.save(Mockito.any())).thenReturn(messageAuction);
        when(auctionRepository.save(Mockito.any())).thenReturn(auction);
        // Act
        String message = "Error ocurrred while updating customer";
        Throwable exception = catchThrowable(() -> {
            messageAuctionServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create Message But Error Ocurred While Updating Message")
    void WhenCreateMessageButErrorOcurredWhileUpdatingMessage() {
        // Arrange
        MessageAuctionRequest request = new MessageAuctionRequest();
        request.setAuctionId(1L);
        request.setCustomerId(1L);
        request.setPrice(200.0);

        when(auctionRepository.getAuctionById(1L)).thenReturn(Optional.of(auction));
        when(customerRepository.getCustomerById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(customer));
        when(customerRepository.save(Mockito.any())).thenReturn(customer);
        when(messageAuctionRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        when(auctionRepository.save(Mockito.any())).thenReturn(auction);
        // Act
        String message = "Error ocurrred while updating message";
        Throwable exception = catchThrowable(() -> {
            messageAuctionServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create Message But Error Ocurred While Updating Price Auction")
    void WhenCreateMessageButErrorOcurredWhileUpdatingPriceAuction() {
        // Arrange
        MessageAuctionRequest request = new MessageAuctionRequest();
        request.setAuctionId(1L);
        request.setCustomerId(1L);
        request.setPrice(200.0);

        when(auctionRepository.getAuctionById(1L)).thenReturn(Optional.of(auction));
        when(customerRepository.getCustomerById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(customer));
        when(customerRepository.save(Mockito.any())).thenReturn(customer);
        when(messageAuctionRepository.save(Mockito.any())).thenReturn(messageAuction);
        when(auctionRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        // Act
        String message = "Error ocurrred while updating auction price";
        Throwable exception = catchThrowable(() -> {
            messageAuctionServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create Message Is Successful With 2 Customer")
    void WhenCreateMessageIsSuccessfulWith2Customer() {
        // Arrange
        MessageAuctionRequest request = new MessageAuctionRequest();
        request.setAuctionId(1L);
        request.setCustomerId(2L);
        request.setPrice(200.0);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setEmail("email2@hotmail");
        customer2.setDni("789456122");
        customer2.setWallet(1000.0);

        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(customer);

        when(auctionRepository.getAuctionById(1L)).thenReturn(Optional.of(auction2Customer));
        when(customerRepository.getCustomerById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.getCustomerById(2L)).thenReturn(Optional.of(customer2));
        when(customerRepository.saveAll(Mockito.any())).thenReturn(customers);
        when(customerRepository.save(Mockito.any())).thenReturn(customer);
        when(messageAuctionRepository.save(Mockito.any())).thenReturn(messageAuction);
        when(auctionRepository.save(Mockito.any())).thenReturn(auction);
        // Act
        MessageAuctionResponse response = messageAuctionServiceImpl.create(request);
        CustomerResponse customer1Response = customerServiceImpl.getById(1L);
        CustomerResponse customer2Response = customerServiceImpl.getById(2L);

        // Assert
        assertNotNull(response);
        assertEquals(1150, customer1Response.getWallet());
        assertEquals(800, customer2Response.getWallet());
    }

    @Test
    @DisplayName("When Create Message But Error Ocurred While Updating 2 Customer")
    void WhenCreateMessageButErrorOcurredWhileUpdating2Customer() {
        // Arrange
        MessageAuctionRequest request = new MessageAuctionRequest();
        request.setAuctionId(1L);
        request.setCustomerId(2L);
        request.setPrice(200.0);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setEmail("email2@hotmail");
        customer2.setDni("789456122");
        customer2.setWallet(1000.0);

        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(customer);

        when(auctionRepository.getAuctionById(1L)).thenReturn(Optional.of(auction2Customer));
        when(customerRepository.getCustomerById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.getCustomerById(2L)).thenReturn(Optional.of(customer2));
        doThrow(ResourceNotFoundExceptionRequest.class).when(customerRepository).saveAll(Mockito.any());
        when(customerRepository.save(Mockito.any())).thenReturn(customer);
        when(messageAuctionRepository.save(Mockito.any())).thenReturn(messageAuction);
        when(auctionRepository.save(Mockito.any())).thenReturn(auction);
        // Act
        String message = "Error ocurrred while updating customer";
        Throwable exception = catchThrowable(() -> {
            messageAuctionServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }
}
