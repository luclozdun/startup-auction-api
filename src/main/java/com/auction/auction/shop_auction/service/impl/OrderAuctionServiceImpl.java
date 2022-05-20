package com.auction.auction.shop_auction.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.security.repository.CustomerRepository;
import com.auction.auction.shop_auction.dto.OrderAuctionRequest;
import com.auction.auction.shop_auction.dto.OrderAuctionResponse;
import com.auction.auction.shop_auction.model.OrderAuction;
import com.auction.auction.shop_auction.repository.AuctionProductRepository;
import com.auction.auction.shop_auction.repository.OrderAuctionRepository;
import com.auction.auction.shop_auction.service.OrderAuctionService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderAuctionServiceImpl implements OrderAuctionService {

    @Autowired
    private OrderAuctionRepository orderAuctionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuctionProductRepository auctionProductRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<OrderAuctionResponse> getAll() {
        var entities = orderAuctionRepository.findAll();
        var response = entities.stream().map(entity -> mapper.map(entity, OrderAuctionResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public List<OrderAuctionResponse> getAllByCustomerId(Long id) {
        var entities = orderAuctionRepository.findAllByCustomerId(id);
        var response = entities.stream().map(entity -> mapper.map(entity, OrderAuctionResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public List<OrderAuctionResponse> getAllByAuctionProductId(Long id) {
        var entities = orderAuctionRepository.findAllByAuctionProductId(id);
        var response = entities.stream().map(entity -> mapper.map(entity, OrderAuctionResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public OrderAuctionResponse getById(Long id) {
        var entity = orderAuctionRepository.getOrderAuctionById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Order auction not found"));
        var response = mapper.map(entity, OrderAuctionResponse.class);
        return response;
    }

    @Override
    public OrderAuctionResponse create(OrderAuctionRequest request) {
        var customer = customerRepository.getCustomerById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found"));

        var auctionProduct = auctionProductRepository.getAuctionProductById(request.getAuctionId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Auction product not found"));
        var entity = new OrderAuction();
        entity.setAuctionProduct(auctionProduct);
        entity.setCustomer(customer);
        entity.setCreatedAt(new Date());
        entity.setPrice(request.getPrice());
        try {
            orderAuctionRepository.save(entity);
            var response = mapper.map(entity, OrderAuctionResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating order action");
        }
    }

    @Override
    public OrderAuctionResponse update(OrderAuctionRequest request, Long id) {
        var entity = orderAuctionRepository.getOrderAuctionById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Order auction not found"));

        var customer = customerRepository.getCustomerById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found"));

        var auctionProduct = auctionProductRepository.getAuctionProductById(request.getAuctionId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Auction product not found"));

        entity.setAuctionProduct(auctionProduct);
        entity.setCustomer(customer);
        entity.setPrice(request.getPrice());
        try {
            orderAuctionRepository.save(entity);
            var response = mapper.map(entity, OrderAuctionResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating order action");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            orderAuctionRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting order action");
        }
    }

}
