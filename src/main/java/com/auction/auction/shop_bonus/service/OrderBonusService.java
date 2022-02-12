package com.auction.auction.shop_bonus.service;

import java.util.List;

import com.auction.auction.shop_bonus.dto.OrderBonusRequest;
import com.auction.auction.shop_bonus.dto.OrderBonusResponse;

public interface OrderBonusService {
    List<OrderBonusResponse> getAll();

    OrderBonusResponse getById(Long id);

    OrderBonusResponse create(OrderBonusRequest request);

    OrderBonusResponse update(Long id, OrderBonusRequest request);

    void delete(Long id);
}
