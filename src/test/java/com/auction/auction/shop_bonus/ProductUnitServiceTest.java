package com.auction.auction.shop_bonus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.product.model.Category;
import com.auction.auction.product.repository.CategoryRepository;
import com.auction.auction.shop_unit.dto.ProductUnitRequest;
import com.auction.auction.shop_unit.model.ProductUnit;
import com.auction.auction.shop_unit.repository.ProductUnitRepository;
import com.auction.auction.shop_unit.service.impl.ProductUnitServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ProductUnitServiceTest {

    @InjectMocks
    private ProductUnitServiceImpl productUnitServiceImpl;

    @Spy
    private ModelMapper mapper;

    @Spy
    private ProductUnitRepository productUnitRepository;

    @Spy
    private CategoryRepository categoryRepository;

    private ProductUnit productUnit;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        productUnit = new ProductUnit();
        productUnit.setAvaible(true);
        productUnit.setCaracteristic("It's a black computer");
        productUnit.setCategory(new Category());
        productUnit.setId(1L);
        productUnit.setImage1("Image 1");
        productUnit.setImage2("Image 2");
        productUnit.setImage3("Image 3");
        productUnit.setMinStock(0L);
        productUnit.setName("Computer");
        productUnit.setPrice(1550.00);
        productUnit.setStock(15L);
        productUnit.setVideo("Video");
    }

    @Test
    @DisplayName("When findAll product unit successful")
    void findAll() {
        // Arrange
        when(productUnitRepository.findAll()).thenReturn(Arrays.asList(productUnit));

        // Act
        var products = productUnitServiceImpl.getAll();

        // Assert
        assertNotNull(products);
    }

    @Test
    @DisplayName("When find product by id, validate the id, caracteristic and name")
    void findById() {
        // Arrange
        when(productUnitRepository.getProductUnitById(1L)).thenReturn(Optional.of(productUnit));

        // Act
        var product = productUnitServiceImpl.getById(1L);

        // Assert
        assertEquals(1L, product.getId());
        assertEquals("It's a black computer", product.getCaracteristic());
        assertEquals("Computer", product.getName());
    }

    @Test
    @DisplayName("When update product by id and it is successful, validate the id, caracteristic and name")
    void updateById() {
        // Arrange

        ProductUnitRequest request = new ProductUnitRequest();
        request.setAvaible(true);
        request.setCaracteristic("It's a black laptop");
        request.setCategoryId(1L);
        request.setImage1("Image 1");
        request.setImage2("Image 2");
        request.setImage3("Image 3");
        request.setName("Laptop");
        request.setPrice(1000.00);
        request.setStock(10L);
        request.setVideo("Video");

        ProductUnit product = productUnit;
        product.setAvaible(request.getAvaible());
        product.setCategory(new Category());
        product.setPrice(request.getPrice());
        product.setName(request.getName());
        product.setStock(request.getStock());

        Category category = new Category();
        category.setDescription("Todo lo relacionado a la tecnologia");
        category.setName("Tecnologia");
        category.setId(1L);

        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.of(category));
        when(productUnitRepository.getProductUnitById(1L)).thenReturn(Optional.of(productUnit));
        when(productUnitRepository.save(productUnit)).thenReturn(product);
        // Act
        var response = productUnitServiceImpl.update(1L, request);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("It's a black laptop", response.getCaracteristic());
        assertEquals("Laptop", response.getName());
    }

    @Test
    @DisplayName("When Delete A ProductUnit Not Found")
    void WhenDeleteAProductUnitNotFound() {
        // Arrange
        doNothing().when(productUnitRepository).deleteById(1L);

        productUnitServiceImpl.delete(1L);

        when(productUnitRepository.getProductUnitById(1L)).thenReturn(Optional.empty());
        // Act

        String message = "Product unit not found";

        Throwable exception = catchThrowable(() -> {
            productUnitServiceImpl.getById(1L);
        });

        System.out.println("x");

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create A ProductUnit Return Exception While Saving A Product")
    void WhenCreateAProductUnitReturnExceptionWhileSavingAProduct() {
        // Arrange
        ProductUnitRequest request = new ProductUnitRequest();

        request.setAvaible(true);
        request.setCaracteristic("It's a black laptop");
        request.setCategoryId(1L);
        request.setImage1("Image 1");
        request.setImage2("Image 2");
        request.setImage3("Image 3");
        request.setName("Laptop");
        request.setPrice(1000.00);
        request.setStock(10L);
        request.setVideo("Video");

        Category category = new Category();
        category.setDescription("Todo lo relacionado a la tecnologia");
        category.setName("Tecnologia");
        category.setId(1L);

        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.of(category));
        when(productUnitRepository.save(Mockito.any(ProductUnit.class)))
                .thenThrow(ResourceNotFoundExceptionRequest.class);
        // Act
        String message = "Error ocurred while creating product unit";

        Throwable exception = catchThrowable(() -> {
            productUnitServiceImpl.create(request);
        });
        // Assert

        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete A Product Unit But Ocurred An Error")
    void WhenDeleteAProductUnitButOcurredAnError() {
        // Arrange
        doThrow(ResourceNotFoundExceptionRequest.class).when(productUnitRepository).deleteById(1L);

        // Act
        String message = "Error ocurred while deleting product unit";
        Throwable exception = catchThrowable(() -> {
            productUnitServiceImpl.delete(1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When create a product unit but not exist category id")
    void WhenCreateAProductUnitButNotExistCategoryId() {
        ProductUnitRequest request = new ProductUnitRequest();

        request.setAvaible(true);
        request.setCaracteristic("It's a black laptop");
        request.setCategoryId(1L);
        request.setImage1("Image 1");
        request.setImage2("Image 2");
        request.setImage3("Image 3");
        request.setName("Laptop");
        request.setPrice(1000.00);
        request.setStock(10L);
        request.setVideo("Video");

        Category category = new Category();
        category.setDescription("Todo lo relacionado a la tecnologia");
        category.setName("Tecnologia");
        category.setId(1L);

        when(categoryRepository.getCategoryById(2L)).thenReturn(Optional.empty());
        when(productUnitRepository.save(Mockito.any(ProductUnit.class)))
                .thenReturn(new ProductUnit());
        // Act
        String message = "Category not found";

        Throwable exception = catchThrowable(() -> {
            productUnitServiceImpl.create(request);
        });
        // Assert

        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete Product Unit It Is Successful")
    void WhenDeleteProductUnitItIsSuccessful() {
        // Arrange
        doNothing().when(productUnitRepository).deleteById(1L);

        // Act
        Throwable exception = catchThrowable(() -> {
            productUnitServiceImpl.delete(1L);
        });

        // Assert
        assertNull(exception);
    }
}
