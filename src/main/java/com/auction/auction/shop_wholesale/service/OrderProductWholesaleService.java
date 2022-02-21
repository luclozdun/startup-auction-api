package com.auction.auction.shop_wholesale.service;

import java.util.List;

import com.auction.auction.shop_wholesale.dto.OrderProductWholesaleRequest;
import com.auction.auction.shop_wholesale.dto.OrderProductWholesaleResponse;

public interface OrderProductWholesaleService {
    List<OrderProductWholesaleResponse> getAll();

    List<OrderProductWholesaleResponse> create(OrderProductWholesaleRequest request);

    void deleteByOrderId(Long id);
}
