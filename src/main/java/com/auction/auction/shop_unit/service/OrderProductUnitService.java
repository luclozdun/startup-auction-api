package com.auction.auction.shop_unit.service;

import java.util.List;

import com.auction.auction.shop_unit.dto.OrderProductUnitRequest;
import com.auction.auction.shop_unit.dto.OrderProductUnitResponse;

public interface OrderProductUnitService {
    List<OrderProductUnitResponse> getAll();

    List<OrderProductUnitResponse> create(OrderProductUnitRequest request);

    List<OrderProductUnitResponse> getByOrderId(Long id);

    void deleteByOrderId(Long id);
}
