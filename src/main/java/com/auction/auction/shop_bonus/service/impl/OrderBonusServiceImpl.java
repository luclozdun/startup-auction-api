package com.auction.auction.shop_bonus.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.security.repository.CustomerRepository;
import com.auction.auction.shop_bonus.dto.OrderBonusRequest;
import com.auction.auction.shop_bonus.dto.OrderBonusResponse;
import com.auction.auction.shop_bonus.model.OrderBonus;
import com.auction.auction.shop_bonus.repository.OrderBonusRepository;
import com.auction.auction.shop_bonus.service.OrderBonusService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderBonusServiceImpl implements OrderBonusService {

    @Autowired
    private OrderBonusRepository orderBonusRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<OrderBonusResponse> getAll() {
        var ordersBonus = orderBonusRepository.findAll();
        var response = ordersBonus.stream().map(orderBonus -> mapper.map(orderBonus, OrderBonusResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public OrderBonusResponse getById(Long id) {
        var orderBonus = orderBonusRepository.getOrderBonusById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Order bonus not found"));
        var response = mapper.map(orderBonus, OrderBonusResponse.class);
        return response;
    }

    @Override
    public OrderBonusResponse create(OrderBonusRequest request) {
        var customer = customerRepository.getCustomerById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found"));
        var orderBonus = new OrderBonus();
        orderBonus.setDate(request.getDate());
        orderBonus.setTotal(request.getTotal());
        orderBonus.setCustomer(customer);
        try {
            orderBonusRepository.save(orderBonus);
            var response = mapper.map(orderBonus, OrderBonusResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating order bonus");
        }
    }

    @Override
    public OrderBonusResponse update(Long id, OrderBonusRequest request) {
        var orderBonus = orderBonusRepository.getOrderBonusById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Order bonus not found"));
        if (orderBonus.getCustomer().getId() != request.getCustomerId()) {
            var customer = customerRepository.getCustomerById(request.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found"));
            orderBonus.setCustomer(customer);
        }
        orderBonus.setDate(request.getDate());
        orderBonus.setTotal(request.getTotal());
        try {
            orderBonusRepository.save(orderBonus);
            var response = mapper.map(orderBonus, OrderBonusResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating order bonus");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            orderBonusRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting order bonus");
        }
    }
}
