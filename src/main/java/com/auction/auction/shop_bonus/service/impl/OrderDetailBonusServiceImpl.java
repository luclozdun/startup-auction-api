package com.auction.auction.shop_bonus.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.security.repository.CustomerRepository;
import com.auction.auction.shop_bonus.dto.OrderDetailBonusRequest;
import com.auction.auction.shop_bonus.dto.OrderDetailBonusResponse;
import com.auction.auction.shop_bonus.model.OrderBonus;
import com.auction.auction.shop_bonus.model.OrderDetailBonus;
import com.auction.auction.shop_bonus.model.ProductBonus;
import com.auction.auction.shop_bonus.repository.OrderBonusRepository;
import com.auction.auction.shop_bonus.repository.OrderDetailBonusRepository;
import com.auction.auction.shop_bonus.repository.ProductBonusRepository;
import com.auction.auction.shop_bonus.service.OrderDetailBonusService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailBonusServiceImpl implements OrderDetailBonusService {

    @Autowired
    private OrderBonusRepository orderBonusRepository;

    @Autowired
    private OrderDetailBonusRepository orderDetailBonusRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductBonusRepository productBonusRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<OrderDetailBonusResponse> getAll() {
        var orderDetails = orderDetailBonusRepository.findAll();
        var response = orderDetails.stream().map(orderDetail -> mapper.map(orderDetail, OrderDetailBonusResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public List<OrderDetailBonusResponse> create(OrderDetailBonusRequest request) {

        var orderBonus = new OrderBonus();
        var customer = customerRepository.getCustomerById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found"));

        orderBonus.setCustomer(customer);
        orderBonus.setDate(request.getDate());
        orderBonusRepository.save(orderBonus);
        var orderDetails = new ArrayList<OrderDetailBonus>();
        var products = new ArrayList<ProductBonus>();
        request.getProducts().forEach(data -> {
            // ******************
            // Verify product and stock
            // ******************

            var product = productBonusRepository.getProductBonusById(data.getProductId())
                    .orElse(null);
            if (product == null) {
                throw new ResourceNotFoundExceptionRequest("Product not found by id");
            }
            var stock = product.getStock() - data.getQuantify();
            var total = data.getQuantify() * product.getPrice();
            if (stock < 0) {
                throw new ResourceNotFoundExceptionRequest("Stock not valid");
            }
            product.setStock(stock);

            var orderDetail = new OrderDetailBonus();
            orderDetail.setOrderBonusId(orderBonus);
            orderDetail.setProductBonusId(product);
            orderDetail.setQuantify(data.getQuantify());
            orderDetail.setSubtotal(total);
            orderDetails.add(orderDetail);
            products.add(product);
        });

        // ******************
        // Update product by stock
        // ******************

        try {
            orderDetailBonusRepository.saveAll(orderDetails);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating orders details");
        }

        try {
            productBonusRepository.saveAll(products);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating products bonus");
        }

        var response = orderDetails.stream().map(order -> mapper.map(order, OrderDetailBonusResponse.class))
                .collect(Collectors.toList());
        return response;
    }

}
