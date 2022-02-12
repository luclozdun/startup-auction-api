package com.auction.auction.shop_bonus.service;

import java.util.List;

import com.auction.auction.shop_bonus.dto.OrderDetailBonusRequest;
import com.auction.auction.shop_bonus.dto.OrderDetailBonusResponse;

public interface OrderDetailBonusService {
    List<OrderDetailBonusResponse> getAll();

    List<OrderDetailBonusResponse> create(OrderDetailBonusRequest request);
}
