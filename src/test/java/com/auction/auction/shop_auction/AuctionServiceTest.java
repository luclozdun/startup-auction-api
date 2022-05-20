package com.auction.auction.shop_auction;

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
import com.auction.auction.product.model.Category;
import com.auction.auction.security.model.Employee;
import com.auction.auction.shop_auction.dto.AuctionRequest;
import com.auction.auction.shop_auction.dto.AuctionResponse;
import com.auction.auction.shop_auction.model.Auction;
import com.auction.auction.shop_auction.model.AuctionProduct;
import com.auction.auction.shop_auction.repository.AuctionProductRepository;
import com.auction.auction.shop_auction.repository.AuctionRepository;
import com.auction.auction.shop_auction.service.impl.AuctionServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.*;

public class AuctionServiceTest {

    @Spy
    private ModelMapper mapper;

    @Spy
    private AuctionRepository auctionRepository;

    @Spy
    private AuctionProductRepository auctionProductRepository;

    @InjectMocks
    private AuctionServiceImpl auctionServiceImpl;

    private Auction auction;

    private AuctionProduct auctionProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        auction = new Auction();
        auction.setActive(true);
        auction.setAuctionProduct(new AuctionProduct());
        auction.setCreatedAt(new Date());
        auction.setCustomer(null);
        auction.setFinishedAt(new Date());
        auction.setPrice(1550L);
        auction.setId(1L);

        auctionProduct = new AuctionProduct();
        auctionProduct.setCaracteristic("It's a black computer");
        auctionProduct.setImage1("Image 1");
        auctionProduct.setImage2("Image 2");
        auctionProduct.setImage3("Image 3");
        auctionProduct.setPrice(1550L);
        auctionProduct.setName("Computer");
        auctionProduct.setPrice(1550L);
        auctionProduct.setVideo("Video");
        auctionProduct.setEmployee(new Employee());
        auctionProduct.setCategory(new Category());
        auctionProduct.setCreatedAt(new Date());
        auctionProduct.setId(1L);
        auctionProduct.setStock(5L);
        auctionProduct.setUpdatedAt(new Date());
    }

    @Test
    @DisplayName("When Find All Return Array List Of Auction")
    void WhenFindAllReturnArrayListOfAuction() {
        // Arrange
        Auction newAuction = new Auction();
        newAuction = new Auction();
        auction.setActive(true);
        auction.setAuctionProduct(new AuctionProduct());
        auction.setCreatedAt(new Date());
        auction.setCustomer(null);
        auction.setFinishedAt(new Date());
        auction.setPrice(10L);
        auction.setId(2L);

        ArrayList<Auction> auctions = new ArrayList<Auction>();
        auctions.add(auction);
        auctions.add(newAuction);

        when(auctionRepository.findAll()).thenReturn(auctions);

        // Act
        List<AuctionResponse> response = auctionServiceImpl.getAll();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    @DisplayName("When Find By Id Return Auction")
    void WhenFindByIdReturnAuction() {
        // Arrange
        when(auctionRepository.getAuctionById(1L)).thenReturn(Optional.of(auction));

        // Act
        AuctionResponse response = auctionServiceImpl.getById(1L);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals(1550L, response.getPrice());
    }

    @Test
    @DisplayName("When Find By Id But Not Exist")
    void WhenFindByIdButNotExist() {
        // Arrange
        when(auctionRepository.getAuctionById(2L)).thenReturn(Optional.empty());

        // Act
        String message = "Auction not found by id";

        Throwable exception = catchThrowable(() -> {
            auctionServiceImpl.getById(2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create Auction Is Successful")
    void WhenCreateAuctionIsSuccessful() {
        // Arrange
        AuctionRequest request = new AuctionRequest();
        request.setActive(true);
        request.setAuctionProductId(1L);
        request.setFinishedAt(new Date());

        when(auctionProductRepository.getAuctionProductById(1L)).thenReturn(Optional.of(auctionProduct));
        when(auctionRepository.save(Mockito.any())).thenReturn(auction);

        // Act
        AuctionResponse response = auctionServiceImpl.create(request);
        // Assert
        assertNotNull(response);
    }

    @Test
    @DisplayName("When Create Auction But Auction Product Not Found")
    void WhenCreateAuctionButAuctionProductNotFound() {
        // Arrange
        AuctionRequest request = new AuctionRequest();
        request.setActive(true);
        request.setAuctionProductId(1L);
        request.setFinishedAt(new Date());

        when(auctionProductRepository.getAuctionProductById(1L)).thenReturn(Optional.empty());
        when(auctionRepository.save(Mockito.any())).thenReturn(auction);

        // Act
        String message = "Auction product not found";
        Throwable exception = catchThrowable(() -> {
            auctionServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create Auction But Error Ocurred While Creating")
    void WhenCreateAuctionButErrorOcurredWhileCreating() {
        // Arrange
        AuctionRequest request = new AuctionRequest();
        request.setActive(true);
        request.setAuctionProductId(1L);
        request.setFinishedAt(new Date());

        when(auctionProductRepository.getAuctionProductById(1L)).thenReturn(Optional.of(auctionProduct));
        when(auctionRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        // Act
        String message = "Error ocurred while creating auction";
        Throwable exception = catchThrowable(() -> {
            auctionServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update Auction Is Successful")
    void WhenUpdateAuctionIsSuccessful() {
        // Arrange
        AuctionRequest request = new AuctionRequest();
        request.setActive(true);
        request.setAuctionProductId(1L);
        request.setFinishedAt(new Date());

        when(auctionProductRepository.getAuctionProductById(1L)).thenReturn(Optional.of(auctionProduct));
        when(auctionRepository.save(Mockito.any())).thenReturn(auction);
        when(auctionRepository.getAuctionById(1L)).thenReturn(Optional.of(auction));
        // Act
        AuctionResponse response = auctionServiceImpl.update(request, 1L);

        // Assert
        assertNotNull(response);
    }

    @Test
    @DisplayName("When Update Auction But Not Exist Auction")
    void WhenUpdateAuctionButNotExistAuction() {
        // Arrange
        AuctionRequest request = new AuctionRequest();
        request.setActive(true);
        request.setAuctionProductId(1L);
        request.setFinishedAt(new Date());

        when(auctionProductRepository.getAuctionProductById(1L)).thenReturn(Optional.of(auctionProduct));
        when(auctionRepository.save(Mockito.any())).thenReturn(auction);
        when(auctionRepository.getAuctionById(1L)).thenReturn(Optional.empty());
        // Act
        String message = "Auction not found";
        Throwable exception = catchThrowable(() -> {
            auctionServiceImpl.update(request, 1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update Auction But Error Ocurred While Updating")
    void WhenUpdateAuctionButErrorOcurredWhileUpdating() {
        // Arrange
        AuctionRequest request = new AuctionRequest();
        request.setActive(true);
        request.setAuctionProductId(1L);
        request.setFinishedAt(new Date());

        when(auctionProductRepository.getAuctionProductById(1L)).thenReturn(Optional.of(auctionProduct));
        when(auctionRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        when(auctionRepository.getAuctionById(1L)).thenReturn(Optional.of(auction));
        // Act
        String message = "Error ocurred while updating auction";
        Throwable exception = catchThrowable(() -> {
            auctionServiceImpl.update(request, 1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete Auction Is Successful")
    void WhenDeleteAuctionIsSuccessful() {
        // Arrange
        doNothing().when(auctionRepository).deleteById(1L);
        // Act
        Throwable exception = catchThrowable(() -> {
            auctionServiceImpl.delete(1L);
        });

        // Assert
        assertNull(exception);
    }

    @Test
    @DisplayName("When Delete Auction But Error Ocurred While Deleting")
    void WhenDeleteAuctionButErrorOcurredWhileDeleting() {
        // Arrange
        doThrow(ResourceNotFoundExceptionRequest.class).when(auctionRepository).deleteById(1L);
        // Act
        String message = "Error ocurred while deleting auction";
        Throwable exception = catchThrowable(() -> {
            auctionServiceImpl.delete(1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }
}