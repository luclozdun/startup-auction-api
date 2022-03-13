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
import com.auction.auction.product.repository.CategoryRepository;
import com.auction.auction.shop_auction.dto.AuctionRequest;
import com.auction.auction.shop_auction.dto.AuctionResponse;
import com.auction.auction.shop_auction.model.Auction;
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

public class ProductAuctionServiceTest {

    @Spy
    private ModelMapper mapper;

    @Spy
    private AuctionRepository auctionRepository;

    @Spy
    private CategoryRepository categoryRepository;

    @InjectMocks
    private AuctionServiceImpl auctionServiceImpl;

    private Auction auction;

    private Category category;

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
        auction.setPrice(1550.00);
        auction.setCustomer(null);
        auction.setVideo("Video");

        category = new Category();
        category.setId(1L);
        category.setDescription("Everything Tecnology like computer, movile, nintendo");
        category.setName("Tecnology");
    }

    @Test
    @DisplayName("When Find All Return Array List Of Auction")
    void WhenFindAllReturnArrayListOfAuction() {
        // Arrange
        Auction newAuction = new Auction();
        newAuction = new Auction();
        newAuction.setAvaible(true);
        newAuction.setCaracteristic("It's a black computer");
        newAuction.setCategory(new Category());
        newAuction.setId(2L);
        newAuction.setImage1("Image 1");
        newAuction.setImage2("Image 2");
        newAuction.setImage3("Image 3");
        newAuction.setPriceBase(1550.00);
        newAuction.setName("Computer");
        newAuction.setPrice(1550.00);
        newAuction.setCustomer(null);
        newAuction.setVideo("Video");

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
    @DisplayName("When Find By Id Return Auction, Valid Id, Name, Caracteristic And Price")
    void WhenFindByIdReturnAuctionValidIdNameCaracteristicAndPrice() {
        // Arrange
        when(auctionRepository.getAuctionById(1L)).thenReturn(Optional.of(auction));

        // Act
        AuctionResponse response = auctionServiceImpl.getById(1L);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("Computer", response.getName());
        assertEquals("It's a black computer", response.getCaracteristic());
        assertEquals(1550.0, response.getPrice());
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
        request.setAvaible(true);
        request.setCaracteristic("It's a black computer");
        request.setCustomerId(1L);
        request.setCategoryId(1L);
        request.setImage1("Image 1");
        request.setImage2("Image 2");
        request.setImage3("Image 3");
        request.setPrice(1550.00);
        request.setName("Computer");
        request.setPrice(1550.00);
        request.setVideo("Video");
        request.setLastDate(new Date());

        when(auctionRepository.save(Mockito.any())).thenReturn(auction);
        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.of(category));

        // Act
        AuctionResponse response = auctionServiceImpl.create(request);

        // Assert
        assertNotNull(response);
    }

    @Test
    @DisplayName("When Create Auction But Not Exist Category")
    void WhenCreateAuctionButNotExistCategory() {
        // Arrange
        AuctionRequest request = new AuctionRequest();
        request.setAvaible(true);
        request.setCaracteristic("It's a black computer");
        request.setCustomerId(1L);
        request.setCategoryId(1L);
        request.setImage1("Image 1");
        request.setImage2("Image 2");
        request.setImage3("Image 3");
        request.setPrice(1550.00);
        request.setName("Computer");
        request.setPrice(1550.00);
        request.setVideo("Video");
        request.setLastDate(new Date());

        when(auctionRepository.save(Mockito.any())).thenReturn(auction);
        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.empty());

        // Act
        String message = "Category not found";
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
        request.setAvaible(true);
        request.setCaracteristic("It's a black computer");
        request.setCustomerId(1L);
        request.setCategoryId(1L);
        request.setImage1("Image 1");
        request.setImage2("Image 2");
        request.setImage3("Image 3");
        request.setPrice(1550.00);
        request.setName("Computer");
        request.setPrice(1550.00);
        request.setVideo("Video");
        request.setLastDate(new Date());

        when(auctionRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.of(category));

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
        request.setAvaible(true);
        request.setCaracteristic("It's a black computer");
        request.setCustomerId(1L);
        request.setCategoryId(1L);
        request.setImage1("Image 1");
        request.setImage2("Image 2");
        request.setImage3("Image 3");
        request.setPrice(1550.00);
        request.setName("Computer");
        request.setPrice(1550.00);
        request.setVideo("Video");
        request.setLastDate(new Date());

        when(auctionRepository.save(Mockito.any())).thenReturn(auction);
        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.of(category));
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
        request.setAvaible(true);
        request.setCaracteristic("It's a black computer");
        request.setCustomerId(1L);
        request.setCategoryId(1L);
        request.setImage1("Image 1");
        request.setImage2("Image 2");
        request.setImage3("Image 3");
        request.setPrice(1550.00);
        request.setName("Computer");
        request.setPrice(1550.00);
        request.setVideo("Video");
        request.setLastDate(new Date());

        when(auctionRepository.save(Mockito.any())).thenReturn(auction);
        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.of(category));
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
    @DisplayName("When Update Auction But Not Exist Category")
    void WhenUpdateAuctionButNotExistCategory() {
        // Arrange
        AuctionRequest request = new AuctionRequest();
        request.setAvaible(true);
        request.setCaracteristic("It's a black computer");
        request.setCustomerId(1L);
        request.setCategoryId(1L);
        request.setImage1("Image 1");
        request.setImage2("Image 2");
        request.setImage3("Image 3");
        request.setPrice(1550.00);
        request.setName("Computer");
        request.setPrice(1550.00);
        request.setVideo("Video");
        request.setLastDate(new Date());

        when(auctionRepository.save(Mockito.any())).thenReturn(auction);
        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.empty());
        when(auctionRepository.getAuctionById(1L)).thenReturn(Optional.of(auction));
        // Act
        String message = "Category not found";
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
        request.setAvaible(true);
        request.setCaracteristic("It's a black computer");
        request.setCustomerId(1L);
        request.setCategoryId(1L);
        request.setImage1("Image 1");
        request.setImage2("Image 2");
        request.setImage3("Image 3");
        request.setPrice(1550.00);
        request.setName("Computer");
        request.setPrice(1550.00);
        request.setVideo("Video");
        request.setLastDate(new Date());

        when(auctionRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.of(category));
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