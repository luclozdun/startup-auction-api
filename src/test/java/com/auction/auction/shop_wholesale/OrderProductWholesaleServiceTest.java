package com.auction.auction.shop_wholesale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.product.model.Category;
import com.auction.auction.shop_wholesale.dto.OrderProductQuantifyRequest;
import com.auction.auction.shop_wholesale.dto.OrderProductWholesaleRequest;
import com.auction.auction.shop_wholesale.dto.OrderProductWholesaleResponse;
import com.auction.auction.shop_wholesale.model.OrderProductWholesale;
import com.auction.auction.shop_wholesale.model.OrderProductWholesaleId;
import com.auction.auction.shop_wholesale.model.OrderWholesale;
import com.auction.auction.shop_wholesale.model.ProductWholesale;
import com.auction.auction.shop_wholesale.repository.OrderProductWholesaleRepository;
import com.auction.auction.shop_wholesale.repository.OrderWholesaleRepository;
import com.auction.auction.shop_wholesale.repository.ProductWholesaleRepository;
import com.auction.auction.shop_wholesale.service.impl.OrderProductWholesaleServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.*;

public class OrderProductWholesaleServiceTest {

    @Spy
    private ModelMapper mapper;

    @Spy
    private OrderWholesaleRepository orderWholesaleRepository;

    @Spy
    private OrderProductWholesaleRepository orderProductWholesaleRepository;

    @Spy
    private ProductWholesaleRepository productWholesaleRepository;

    @InjectMocks
    private OrderProductWholesaleServiceImpl orderProductWholesaleServiceImpl;

    private OrderProductWholesale orderProductWholesale;

    private ProductWholesale productWholesale;

    private OrderWholesale orderWholesale;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productWholesale = new ProductWholesale();
        productWholesale.setAvaible(true);
        productWholesale.setCaracteristic("It's a furnite");
        productWholesale.setCategory(new Category());
        productWholesale.setId(1L);
        productWholesale.setImage1("image1");
        productWholesale.setImage2("image2");
        productWholesale.setImage3("image3");
        productWholesale.setName("Furnance");
        productWholesale.setPrice(100.0);
        productWholesale.setStock(10L);
        productWholesale.setUnid(45L);
        productWholesale.setVideo("video");

        orderWholesale = new OrderWholesale();
        orderWholesale.setCreated(new Date());
        orderWholesale.setId(1L);

        OrderProductWholesaleId orderProductWholesaleId = new OrderProductWholesaleId();
        orderProductWholesaleId.setOrderWholesaleId(1L);
        orderProductWholesaleId.setProductWholesaleId(1L);

        orderProductWholesale = new OrderProductWholesale();
        orderProductWholesale.setOrderProductWholesaleId(orderProductWholesaleId);
        orderProductWholesale.setOrderWholesale(orderWholesale);
        orderProductWholesale.setProductWholesale(productWholesale);
        orderProductWholesale.setQuantify(150L);
    }

    @Test
    @DisplayName("When Find All Return Array Order Product Whole Sale")
    void WhenFindAllReturnArrayOrderProductWholeSale() {
        // Arrange
        OrderWholesale orderWholesale2 = new OrderWholesale();
        orderWholesale2.setCreated(new Date());
        orderWholesale2.setId(2L);

        OrderProductWholesaleId orderProductWholesaleId = new OrderProductWholesaleId();
        orderProductWholesaleId.setOrderWholesaleId(2L);
        orderProductWholesaleId.setProductWholesaleId(1L);

        OrderProductWholesale orderProductWholesale2 = new OrderProductWholesale();
        orderProductWholesale2.setOrderProductWholesaleId(orderProductWholesaleId);
        orderProductWholesale2.setOrderWholesale(orderWholesale2);
        orderProductWholesale2.setProductWholesale(productWholesale);
        orderProductWholesale2.setQuantify(150L);

        ArrayList<OrderProductWholesale> orders = new ArrayList<OrderProductWholesale>();
        orders.add(orderProductWholesale2);
        orders.add(orderProductWholesale);

        when(orderProductWholesaleRepository.findAll()).thenReturn(orders);
        // Act
        List<OrderProductWholesaleResponse> response = orderProductWholesaleServiceImpl.getAll();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    @DisplayName("When Create Order Product Wholesale Is Successful")
    void WhenCreateOrderProductWholesaleIsSuccessful() {
        // Arrange
        OrderProductQuantifyRequest orderProductQuantifyRequest = new OrderProductQuantifyRequest();
        orderProductQuantifyRequest.setProductId(1L);
        orderProductQuantifyRequest.setQuantify(1L);

        OrderProductWholesaleRequest request = new OrderProductWholesaleRequest();
        request.setCreated(new Date());
        request.setOrderProductQuantifyRequests(Arrays.asList(orderProductQuantifyRequest));

        when(orderWholesaleRepository.save(Mockito.any())).thenReturn(orderWholesale);
        when(productWholesaleRepository.getProductWholesaleById(1L)).thenReturn(Optional.of(productWholesale));
        when(orderProductWholesaleRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(orderProductWholesale));
        when(productWholesaleRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(productWholesale));

        // Act
        List<OrderProductWholesaleResponse> response = orderProductWholesaleServiceImpl.create(request);

        // Assert
        assertNotNull(response);
    }

    @Test
    @DisplayName("When Create Order Product Wholesale But Error Ocurred While Creating Order Wholesale")
    void WhenCreateOrderProductWholesaleButErrorOcurredWhileCreatingOrderWholesale() {
        // Arrange
        OrderProductQuantifyRequest orderProductQuantifyRequest = new OrderProductQuantifyRequest();
        orderProductQuantifyRequest.setProductId(1L);
        orderProductQuantifyRequest.setQuantify(1L);

        OrderProductWholesaleRequest request = new OrderProductWholesaleRequest();
        request.setCreated(new Date());
        request.setOrderProductQuantifyRequests(Arrays.asList(orderProductQuantifyRequest));

        when(orderWholesaleRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        when(productWholesaleRepository.getProductWholesaleById(1L)).thenReturn(Optional.of(productWholesale));
        when(orderProductWholesaleRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(orderProductWholesale));
        when(productWholesaleRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(productWholesale));

        // Act
        String message = "Error ocurred while creating order wholesale";
        Throwable exception = catchThrowable(() -> {
            orderProductWholesaleServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create Order Product Wholesale But Product Not Found")
    void WhenCreateOrderProductWholesaleButProductNotFound() {
        // Arrange
        OrderProductQuantifyRequest orderProductQuantifyRequest = new OrderProductQuantifyRequest();
        orderProductQuantifyRequest.setProductId(1L);
        orderProductQuantifyRequest.setQuantify(1L);

        OrderProductWholesaleRequest request = new OrderProductWholesaleRequest();
        request.setCreated(new Date());
        request.setOrderProductQuantifyRequests(Arrays.asList(orderProductQuantifyRequest));

        when(orderWholesaleRepository.save(Mockito.any())).thenReturn(orderWholesale);
        when(productWholesaleRepository.getProductWholesaleById(1L)).thenReturn(Optional.empty());
        when(orderProductWholesaleRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(orderProductWholesale));
        when(productWholesaleRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(productWholesale));

        // Act
        String message = "Product not found";
        Throwable exception = catchThrowable(() -> {
            orderProductWholesaleServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create Order Product Wholesale But Stock Not Avaible")
    void WhenCreateOrderProductWholesaleButStockNotAvaible() {
        // Arrange
        OrderProductQuantifyRequest orderProductQuantifyRequest = new OrderProductQuantifyRequest();
        orderProductQuantifyRequest.setProductId(1L);
        orderProductQuantifyRequest.setQuantify(10000L);

        OrderProductWholesaleRequest request = new OrderProductWholesaleRequest();
        request.setCreated(new Date());
        request.setOrderProductQuantifyRequests(Arrays.asList(orderProductQuantifyRequest));

        when(orderWholesaleRepository.save(Mockito.any())).thenReturn(orderWholesale);
        when(productWholesaleRepository.getProductWholesaleById(1L)).thenReturn(Optional.of(productWholesale));
        when(orderProductWholesaleRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(orderProductWholesale));
        when(productWholesaleRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(productWholesale));

        // Act
        String message = "Stock not avaible";
        Throwable exception = catchThrowable(() -> {
            orderProductWholesaleServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create Order Product Wholesale But Error Ocurred While Order Product Wholesale Creating")
    void WhenCreateOrderProductWholesaleButErrorOcurredWhileOrderProductWholesale() {
        // Arrange
        OrderProductQuantifyRequest orderProductQuantifyRequest = new OrderProductQuantifyRequest();
        orderProductQuantifyRequest.setProductId(1L);
        orderProductQuantifyRequest.setQuantify(1L);

        OrderProductWholesaleRequest request = new OrderProductWholesaleRequest();
        request.setCreated(new Date());
        request.setOrderProductQuantifyRequests(Arrays.asList(orderProductQuantifyRequest));

        when(orderWholesaleRepository.save(Mockito.any())).thenReturn(orderWholesale);
        when(productWholesaleRepository.getProductWholesaleById(1L)).thenReturn(Optional.of(productWholesale));
        doThrow(ResourceNotFoundExceptionRequest.class).when(orderProductWholesaleRepository).saveAll(Mockito.any());
        when(productWholesaleRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(productWholesale));

        // Act
        String message = "Error ocurred while creating orders wholesale";
        Throwable exception = catchThrowable(() -> {
            orderProductWholesaleServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create Order Product Wholesale But Error Ocurred While Product Wholesale Updating")
    void WhenCreateOrderProductWholesaleButErrorOcurredWhileProductWholesaleUpdating() {
        // Arrange
        OrderProductQuantifyRequest orderProductQuantifyRequest = new OrderProductQuantifyRequest();
        orderProductQuantifyRequest.setProductId(1L);
        orderProductQuantifyRequest.setQuantify(1L);

        OrderProductWholesaleRequest request = new OrderProductWholesaleRequest();
        request.setCreated(new Date());
        request.setOrderProductQuantifyRequests(Arrays.asList(orderProductQuantifyRequest));

        when(orderWholesaleRepository.save(Mockito.any())).thenReturn(orderWholesale);
        when(productWholesaleRepository.getProductWholesaleById(1L)).thenReturn(Optional.of(productWholesale));
        when(orderProductWholesaleRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(orderProductWholesale));
        doThrow(ResourceNotFoundExceptionRequest.class).when(productWholesaleRepository).saveAll(Mockito.any());

        // Act
        String message = "Error ocurred while updating product wholesale by stock";
        Throwable exception = catchThrowable(() -> {
            orderProductWholesaleServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delelte Order Product Wholesale Is Successful")
    void WhenDelelteOrderProductWholesaleIsSuccessful() {
        // Arrange
        doNothing().when(orderProductWholesaleRepository).deleteOrderProductWholesaleByOrderId(1L);
        doNothing().when(orderWholesaleRepository).deleteById(1L);

        // Act
        Throwable exception = catchThrowable(() -> {
            orderProductWholesaleServiceImpl.deleteByOrderId(1L);
        });

        // Assert
        assertNull(exception);
    }

    @Test
    @DisplayName("When Delelte Order Product Wholesale But Error Ocurrred While Delete Order Product")
    void WhenDelelteOrderProductWholesaleButErrorOcurrredWhileDeleteOrderProduct() {
        // Arrange
        doThrow(ResourceNotFoundExceptionRequest.class).when(orderProductWholesaleRepository)
                .deleteOrderProductWholesaleByOrderId(1L);
        doNothing().when(orderWholesaleRepository).deleteById(1L);

        // Act
        String message = "Error ocurred while deleting order details of wholesale";
        Throwable exception = catchThrowable(() -> {
            orderProductWholesaleServiceImpl.deleteByOrderId(1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delelte Order Product Wholesale But Error Ocurrred While Delete Order")
    void WhenDelelteOrderProductWholesaleButErrorOcurrredWhileDeleteOrder() {
        // Arrange
        doNothing().when(orderProductWholesaleRepository).deleteOrderProductWholesaleByOrderId(1L);
        doThrow(ResourceNotFoundExceptionRequest.class).when(orderWholesaleRepository).deleteById(1L);

        // Act
        String message = "Error ocurred while deleting order  of wholesale";
        Throwable exception = catchThrowable(() -> {
            orderProductWholesaleServiceImpl.deleteByOrderId(1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

}