package com.auction.auction.shop_wholesale.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.shop_wholesale.dto.OrderProductWholesaleRequest;
import com.auction.auction.shop_wholesale.dto.OrderProductWholesaleResponse;
import com.auction.auction.shop_wholesale.model.OrderProductWholesale;
import com.auction.auction.shop_wholesale.model.OrderWholesale;
import com.auction.auction.shop_wholesale.model.ProductWholesale;
import com.auction.auction.shop_wholesale.repository.OrderProductWholesaleRepository;
import com.auction.auction.shop_wholesale.repository.OrderWholesaleRepository;
import com.auction.auction.shop_wholesale.repository.ProductWholesaleRepository;
import com.auction.auction.shop_wholesale.service.OrderProductWholesaleService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProductWholesaleServiceImpl implements OrderProductWholesaleService {

    @Autowired
    private OrderWholesaleRepository orderWholesaleRepository;

    @Autowired
    private OrderProductWholesaleRepository orderProductWholesaleRepository;

    @Autowired
    private ProductWholesaleRepository productWholesaleRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<OrderProductWholesaleResponse> getAll() {
        var orders = orderProductWholesaleRepository.findAll();
        var response = orders.stream().map(order -> mapper.map(order, OrderProductWholesaleResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public List<OrderProductWholesaleResponse> create(OrderProductWholesaleRequest request) {
        var order = new OrderWholesale();
        order.setCreated(request.getCreated());

        try {
            orderWholesaleRepository.save(order);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating order wholesale");
        }

        var productWholesales = new ArrayList<ProductWholesale>();
        var orderProductWholesales = new ArrayList<OrderProductWholesale>();

        request.getOrderProductQuantifyRequests().forEach(orderDetail -> {
            var orderProduct = new OrderProductWholesale();

            var product = productWholesaleRepository.getProductWholesaleById(orderDetail.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Product not found"));

            var quantify = product.getStock() - orderDetail.getQuantify();
            if (quantify < 0)
                throw new ResourceNotFoundExceptionRequest("Stock not avaible");

            product.setStock(quantify);

            orderProduct.setQuantify(orderDetail.getQuantify());
            orderProduct.setProductWholesale(product);
            orderProduct.setOrderWholesale(order);

            productWholesales.add(product);
            orderProductWholesales.add(orderProduct);
        });

        try {
            orderProductWholesaleRepository.saveAll(orderProductWholesales);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating orders wholesale");
        }

        try {
            productWholesaleRepository.saveAll(productWholesales);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating product wholesale by stock");
        }

        var response = orderProductWholesales.stream()
                .map(orderProduct -> mapper.map(
                        orderProduct, OrderProductWholesaleResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public void deleteByOrderId(Long id) {
        try {
            orderProductWholesaleRepository.deleteOrderProductWholesaleByOrderId(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting order details of wholesale");
        }
        try {
            orderWholesaleRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting order  of wholesale");
        }
    }

}