package com.auction.auction.shop_wholesale;

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
import com.auction.auction.product.model.Category;
import com.auction.auction.product.repository.CategoryRepository;
import com.auction.auction.shop_wholesale.dto.ProductWholeSaleResponse;
import com.auction.auction.shop_wholesale.dto.ProductWholesaleRequest;
import com.auction.auction.shop_wholesale.model.ProductWholesale;
import com.auction.auction.shop_wholesale.repository.ProductWholesaleRepository;
import com.auction.auction.shop_wholesale.service.impl.ProductWholesaleServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.*;

public class ProductWholeSaleServiceTest {

    @Spy
    private ModelMapper mapper;

    @Spy
    private CategoryRepository categoryRepository;

    @Spy
    private ProductWholesaleRepository productWholesaleRepository;

    @InjectMocks
    private ProductWholesaleServiceImpl productWholesaleServiceImpl;

    private Category category;

    private ProductWholesale productWholesale;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category();
        category.setDescription("It furniture");
        category.setName("Furniture");
        category.setId(1L);

        productWholesale = new ProductWholesale();
        productWholesale.setAvaible(true);
        productWholesale.setCaracteristic("It's a furnite");
        productWholesale.setCategory(category);
        productWholesale.setId(1L);
        productWholesale.setImage1("image1");
        productWholesale.setImage2("image2");
        productWholesale.setImage3("image3");
        productWholesale.setName("Furnance");
        productWholesale.setPrice(100.0);
        productWholesale.setStock(10L);
        productWholesale.setUnid(45L);
        productWholesale.setVideo("video");
    }

    @Test
    @DisplayName("When Find All Return Array Product Wholesale")
    void WhenFindAllReturnArrayProductWholesale() {
        // Arrange
        ProductWholesale productWholesale2 = new ProductWholesale();
        productWholesale2.setAvaible(true);
        productWholesale2.setCaracteristic("It's a furnite");
        productWholesale2.setCategory(category);
        productWholesale2.setId(2L);
        productWholesale2.setImage1("image1");
        productWholesale2.setImage2("image2");
        productWholesale2.setImage3("image3");
        productWholesale2.setName("Furnance");
        productWholesale2.setPrice(100.0);
        productWholesale2.setStock(10L);
        productWholesale2.setUnid(45L);
        productWholesale2.setVideo("video");

        ArrayList<ProductWholesale> products = new ArrayList<ProductWholesale>();
        products.add(productWholesale);
        products.add(productWholesale2);

        when(productWholesaleRepository.findAll()).thenReturn(products);
        // Act
        List<ProductWholeSaleResponse> response = productWholesaleServiceImpl.getAll();
        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    @DisplayName("When Find By Id Return Product Wholesale")
    void WhenFindByIdReturnProductWholesale() {
        // Arrange
        when(productWholesaleRepository.getProductWholesaleById(1L)).thenReturn(Optional.of(productWholesale));

        // Act
        ProductWholeSaleResponse response = productWholesaleServiceImpl.getById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Furnance", response.getName());
    }

    @Test
    @DisplayName("When Find By Id But Not Exist")
    void WhenFindByIdButNotExist() {
        // Arrange
        when(productWholesaleRepository.getProductWholesaleById(1L)).thenReturn(Optional.empty());

        // Act
        String message = "Productwholesale not found";
        Throwable exception = catchThrowable(() -> {
            productWholesaleServiceImpl.getById(1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create Product Wholesale Is Successful")
    void WhenCreateProductWholeSaleIsSuccessful() {
        // Arrange
        ProductWholesaleRequest request = new ProductWholesaleRequest();
        request.setAvaible(true);
        request.setCaracteristic("It's a furnite");
        request.setCategoryId(1L);
        request.setImage1("image1");
        request.setImage2("image2");
        request.setImage3("image3");
        request.setName("Furnance");
        request.setPrice(100.0);
        request.setStock(10L);
        request.setUnid(45L);
        request.setVideo("video");

        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.of(category));
        when(productWholesaleRepository.save(Mockito.any())).thenReturn(productWholesale);

        // Act
        ProductWholeSaleResponse response = productWholesaleServiceImpl.create(request);

        // Assert
        assertNotNull(response);
        assertEquals("It's a furnite", request.getCaracteristic());
        assertEquals("Furnance", request.getName());
        assertEquals(100.0, request.getPrice());
    }

    @Test
    @DisplayName("When Create Product Wholesale But Category Not Exist")
    void WhenCreateProductWholeSaleButCategoryNotExist() {
        // Arrange
        ProductWholesaleRequest request = new ProductWholesaleRequest();
        request.setAvaible(true);
        request.setCaracteristic("It's a furnite");
        request.setCategoryId(1L);
        request.setImage1("image1");
        request.setImage2("image2");
        request.setImage3("image3");
        request.setName("Furnance");
        request.setPrice(100.0);
        request.setStock(10L);
        request.setUnid(45L);
        request.setVideo("video");

        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.empty());
        when(productWholesaleRepository.save(Mockito.any())).thenReturn(productWholesale);

        // Act
        String message = "Category not found";

        Throwable exception = catchThrowable(() -> {
            productWholesaleServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create Product Wholesale But Error Ocurred While Saving")
    void WhenCreateProductWholeSaleButErrorOcurredWhileSaving() {
        // Arrange
        ProductWholesaleRequest request = new ProductWholesaleRequest();
        request.setAvaible(true);
        request.setCaracteristic("It's a furnite");
        request.setCategoryId(1L);
        request.setImage1("image1");
        request.setImage2("image2");
        request.setImage3("image3");
        request.setName("Furnance");
        request.setPrice(100.0);
        request.setStock(10L);
        request.setUnid(45L);
        request.setVideo("video");

        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.of(category));
        when(productWholesaleRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);

        // Act
        String message = "Error ocurred while creating Product wholesale";

        Throwable exception = catchThrowable(() -> {
            productWholesaleServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update Product Wholesale Is Successful")
    void WhenUpdateProductWholeSaleIsSuccessful() {
        // Arrange
        ProductWholesaleRequest request = new ProductWholesaleRequest();
        request.setAvaible(true);
        request.setCaracteristic("It's");
        request.setCategoryId(1L);
        request.setImage1("image1");
        request.setImage2("image2");
        request.setImage3("image3");
        request.setName("Toilet");
        request.setPrice(100.0);
        request.setStock(10L);
        request.setUnid(45L);
        request.setVideo("video");

        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.of(category));
        when(productWholesaleRepository.getProductWholesaleById(1L)).thenReturn(Optional.of(productWholesale));
        when(productWholesaleRepository.save(Mockito.any())).thenReturn(productWholesale);

        // Act
        ProductWholeSaleResponse response = productWholesaleServiceImpl.update(request, 1L);

        // Assert
        assertNotNull(response);
        assertNotNull(response);
        assertEquals("It's", request.getCaracteristic());
        assertEquals("Toilet", request.getName());
    }

    @Test
    @DisplayName("When Update Product Wholesale But Category Not Exist")
    void WhenUpdateProductWholeSaleButCategoryNotExist() {
        // Arrange
        ProductWholesaleRequest request = new ProductWholesaleRequest();
        request.setAvaible(true);
        request.setCaracteristic("It's a furnite");
        request.setCategoryId(1L);
        request.setImage1("image1");
        request.setImage2("image2");
        request.setImage3("image3");
        request.setName("Furnance");
        request.setPrice(100.0);
        request.setStock(10L);
        request.setUnid(45L);
        request.setVideo("video");

        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.empty());
        when(productWholesaleRepository.getProductWholesaleById(1L)).thenReturn(Optional.of(productWholesale));
        when(productWholesaleRepository.save(Mockito.any())).thenReturn(productWholesale);

        // Act
        String message = "Category not found";

        Throwable exception = catchThrowable(() -> {
            productWholesaleServiceImpl.update(request, 1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update Product Wholesale But Product Whole Sale Not Found")
    void WhenUpdateProductWholeSaleButProductWholeSaleNotFound() {
        // Arrange
        ProductWholesaleRequest request = new ProductWholesaleRequest();
        request.setAvaible(true);
        request.setCaracteristic("It's a furnite");
        request.setCategoryId(1L);
        request.setImage1("image1");
        request.setImage2("image2");
        request.setImage3("image3");
        request.setName("Furnance");
        request.setPrice(100.0);
        request.setStock(10L);
        request.setUnid(45L);
        request.setVideo("video");

        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.of(category));
        when(productWholesaleRepository.getProductWholesaleById(1L)).thenReturn(Optional.empty());
        when(productWholesaleRepository.save(Mockito.any())).thenReturn(productWholesale);

        // Act
        String message = "Product Wholesale not found";

        Throwable exception = catchThrowable(() -> {
            productWholesaleServiceImpl.update(request, 1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update Product Wholesale But Error Ocurred While Updating")
    void WhenUpdateProductWholeSaleButErrorOcurredWhileUpdating() {
        // Arrange
        ProductWholesaleRequest request = new ProductWholesaleRequest();
        request.setAvaible(true);
        request.setCaracteristic("It's a furnite");
        request.setCategoryId(1L);
        request.setImage1("image1");
        request.setImage2("image2");
        request.setImage3("image3");
        request.setName("Furnance");
        request.setPrice(100.0);
        request.setStock(10L);
        request.setUnid(45L);
        request.setVideo("video");

        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.of(category));
        when(productWholesaleRepository.getProductWholesaleById(1L)).thenReturn(Optional.of(productWholesale));
        when(productWholesaleRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);

        // Act
        String message = "Error ocurred while updating Product wholesale";

        Throwable exception = catchThrowable(() -> {
            productWholesaleServiceImpl.update(request, 1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete Product Wholesale Is Successful")
    void WhenDeleteProductWholeSaleIsSuccessful() {
        // Arrange
        doNothing().when(productWholesaleRepository).deleteById(1L);

        // Act
        Throwable exception = catchThrowable(() -> {
            productWholesaleServiceImpl.delete(1L);
        });

        // Assert
        assertNull(exception);
    }

    @Test
    @DisplayName("When Delete Product Wholesale But Error Ocurred While Deleting")
    void WhenDeleteProductWholeSaleButErrorOcurredWhileDeleting() {
        // Arrange
        doThrow(ResourceNotFoundExceptionRequest.class).when(productWholesaleRepository).deleteById(1L);

        // Act
        String message = "Error ocurred while deleting Product wholesale";
        Throwable exception = catchThrowable(() -> {
            productWholesaleServiceImpl.delete(1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

}