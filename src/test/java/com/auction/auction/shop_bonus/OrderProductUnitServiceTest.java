package com.auction.auction.shop_bonus;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.product.model.Category;
import com.auction.auction.shop_unit.dto.OrderProductUnitRequest;
import com.auction.auction.shop_unit.dto.ProductUnitQuantify;
import com.auction.auction.shop_unit.model.OrderProductUnit;
import com.auction.auction.shop_unit.model.OrderProductUnitId;
import com.auction.auction.shop_unit.model.OrderUnit;
import com.auction.auction.shop_unit.model.ProductUnit;
import com.auction.auction.shop_unit.repository.OrderProductUnitRepository;
import com.auction.auction.shop_unit.repository.OrderUnitRepository;
import com.auction.auction.shop_unit.repository.ProductUnitRepository;
import com.auction.auction.shop_unit.service.impl.OrderProductUnitServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.*;

public class OrderProductUnitServiceTest {

    @Spy
    private OrderProductUnitRepository orderProductUnitRepository;

    @Spy
    private ProductUnitRepository productUnitRepository;

    @Spy
    private OrderUnitRepository orderUnitRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderProductUnitServiceImpl orderProductUnitServiceImpl;

    private OrderProductUnit orderProductUnit;

    private OrderProductUnitId orderProductUnitId;

    private ProductUnit productUnit;

    private OrderUnit orderUnit;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        orderProductUnitId = new OrderProductUnitId();
        orderProductUnitId.setOrderUnitId(1L);
        orderProductUnitId.setProductUnitId(1L);

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

        orderUnit = new OrderUnit();
        orderUnit.setId(1L);
        orderUnit.setCreated_order(new Date());
        orderUnit.setTotal(100.0);

        orderProductUnit = new OrderProductUnit();
        orderProductUnit.setQuantify(1L);
        orderProductUnit.setOrderProductId(orderProductUnitId);
        orderProductUnit.setProductUnit(productUnit);
        orderProductUnit.setOrderUnit(orderUnit);
    }

    @Test
    @DisplayName("When Find All Return An Array List")
    void WhenFindAllReturnAnArrayList() {
        // Arrange
        when(orderProductUnitRepository.findAll()).thenReturn(Arrays.asList(orderProductUnit));

        // Act
        var response = orderProductUnitServiceImpl.getAll();

        // Assert
        assertNotNull(response);
    }

    @Test
    @DisplayName("When Find All By Id Return Array List")
    void WhenFindByIdReturnArrayList() {
        // Arrange
        when(orderProductUnitRepository.getAllByOrderId(1L)).thenReturn(Arrays.asList(orderProductUnit));

        // Act
        var response = orderProductUnitServiceImpl.getByOrderId(1L);

        // Assert
        assertNotNull(response);
    }

    @Test
    @DisplayName("When Create An Order Product Unit Is Successful")
    void WhenCreateAnOrderProductUnitIsSuccessful() {
        // Arrange
        ProductUnitQuantify productUnitQuantify = new ProductUnitQuantify();
        productUnitQuantify.setProductId(1L);
        productUnitQuantify.setQuantify(1L);

        ArrayList<ProductUnitQuantify> productUnitQuantifies = new ArrayList<ProductUnitQuantify>();
        productUnitQuantifies.add(productUnitQuantify);

        OrderProductUnitRequest orderProductUnitRequest = new OrderProductUnitRequest();
        orderProductUnitRequest.setDate_created(new Date());
        orderProductUnitRequest.setProductUnitQuantifies(productUnitQuantifies);

        when(orderProductUnitRepository.save(Mockito.any())).thenReturn(orderProductUnit);
        when(productUnitRepository.getProductUnitById(1L)).thenReturn(Optional.of(productUnit));
        when(orderUnitRepository.getOrderUnitById(1L)).thenReturn(Optional.of(orderUnit));

        // Act
        var ord = orderProductUnitServiceImpl.create(orderProductUnitRequest);

        System.out.println(ord);

        // Assert
        assertNotNull(ord);
    }

    @Test
    @DisplayName("When Create An Order Product Unit But Not Exist The Product Unit")
    void WhenCreateAnOrderProductUnitButNotExistTheProductUnit() {

        // Arrange
        ProductUnitQuantify productUnitQuantify = new ProductUnitQuantify();
        productUnitQuantify.setProductId(1L);
        productUnitQuantify.setQuantify(1L);

        ArrayList<ProductUnitQuantify> productUnitQuantifies = new ArrayList<ProductUnitQuantify>();
        productUnitQuantifies.add(productUnitQuantify);

        OrderProductUnitRequest orderProductUnitRequest = new OrderProductUnitRequest();
        orderProductUnitRequest.setDate_created(new Date());
        orderProductUnitRequest.setProductUnitQuantifies(productUnitQuantifies);

        when(orderProductUnitRepository.save(Mockito.any())).thenReturn(orderProductUnit);
        when(productUnitRepository.getProductUnitById(2L)).thenReturn(Optional.empty());
        when(orderUnitRepository.getOrderUnitById(1L)).thenReturn(Optional.of(orderUnit));

        // Act
        String message = "Product Unit not found";

        Throwable exception = catchThrowable(() -> {
            orderProductUnitServiceImpl.create(orderProductUnitRequest);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create An Order Product Unit Return Error While Saving Order Detail Product Unit")
    void WhenCreateAnOrderProductUnitReturnErrorWhileSavingOrderDetailProductUnit() {

        // Arrange
        ProductUnitQuantify productUnitQuantify = new ProductUnitQuantify();
        productUnitQuantify.setProductId(1L);
        productUnitQuantify.setQuantify(1L);

        ArrayList<ProductUnitQuantify> productUnitQuantifies = new ArrayList<ProductUnitQuantify>();
        productUnitQuantifies.add(productUnitQuantify);

        OrderProductUnitRequest orderProductUnitRequest = new OrderProductUnitRequest();
        orderProductUnitRequest.setDate_created(new Date());
        orderProductUnitRequest.setProductUnitQuantifies(productUnitQuantifies);

        when(orderProductUnitRepository.save(Mockito.any())).thenReturn(orderProductUnit);
        when(productUnitRepository.getProductUnitById(1L)).thenReturn(Optional.of(productUnit));
        when(orderUnitRepository.save(Mockito.any())).thenThrow(ResourceNotFoundExceptionRequest.class);

        // Act
        String message = "Error ocurred while creating order unit";

        Throwable exception = catchThrowable(() -> {
            orderProductUnitServiceImpl.create(orderProductUnitRequest);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create An Order Product Unit But Stock Not Avaible")
    void WhenCreateAnOrderProductUnitButStockNotAvaible() {
        // Arrange
        ProductUnitQuantify productUnitQuantify = new ProductUnitQuantify();
        productUnitQuantify.setProductId(1L);
        productUnitQuantify.setQuantify(1000L);

        ArrayList<ProductUnitQuantify> productUnitQuantifies = new ArrayList<ProductUnitQuantify>();
        productUnitQuantifies.add(productUnitQuantify);

        OrderProductUnitRequest orderProductUnitRequest = new OrderProductUnitRequest();
        orderProductUnitRequest.setDate_created(new Date());
        orderProductUnitRequest.setProductUnitQuantifies(productUnitQuantifies);

        when(orderProductUnitRepository.save(Mockito.any())).thenReturn(orderProductUnit);
        when(productUnitRepository.getProductUnitById(1L)).thenReturn(Optional.of(productUnit));
        when(orderUnitRepository.getOrderUnitById(1L)).thenReturn(Optional.of(orderUnit));

        // Act
        String message = "Stock not avaible";

        Throwable exception = catchThrowable(() -> {
            orderProductUnitServiceImpl.create(orderProductUnitRequest);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete Order Detail Unit But Not Found Order Unit")
    void WhenDeleteOrderDetailUnitButNotFoundOrderUnit() {
        // Arrange

        when(orderUnitRepository.getOrderUnitById(2L)).thenReturn(Optional.empty());
        doNothing().when(orderProductUnitRepository).delete(Mockito.any());
        doNothing().when(orderUnitRepository).delete(Mockito.any());

        // Act
        String message = "Order unit not found";

        Throwable exception = catchThrowable(() -> {
            orderProductUnitServiceImpl.deleteByOrderId(1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessageContaining(message);
    }

    @Test
    @DisplayName("When Delete Order Detail Unit But Error Ocurred While Delete All Order Product Unit")
    void WhenDeleteOrderDetailUnitButErrorOcurredWhileDeleteAllOrderProductUnit() {
        // Arrange

        when(orderUnitRepository.getOrderUnitById(1L)).thenReturn(Optional.of(orderUnit));
        doNothing().when(orderProductUnitRepository).delete(Mockito.any());
        doThrow(ResourceNotFoundExceptionRequest.class).when(orderUnitRepository).delete(Mockito.any());

        // Act
        String message = "Error ocurred while deleting order by id";

        Throwable exception = catchThrowable(() -> {
            orderProductUnitServiceImpl.deleteByOrderId(1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessageContaining(message);
    }

    @Test
    @DisplayName("When Delete Order Detail Unit And It Is Successful")
    void WhenDeleteOrderDetailUnitAndItIsSuccessful() {
        // Arrange

        when(orderUnitRepository.getOrderUnitById(1L)).thenReturn(Optional.of(orderUnit));
        doNothing().when(orderProductUnitRepository).delete(Mockito.any());
        doNothing().when(orderUnitRepository).delete(Mockito.any());

        // Act
        Throwable exception = catchThrowable(() -> {
            orderProductUnitServiceImpl.deleteByOrderId(1L);
        });

        // Assert
        assertNull(exception);
    }
}
