package com.auction.auction.shop_auction.service;

import java.util.List;

import com.auction.auction.shop_auction.dto.OrderAuctionRequest;
import com.auction.auction.shop_auction.dto.OrderAuctionResponse;

public interface OrderAuctionService {
    List<OrderAuctionResponse> getAll();

    List<OrderAuctionResponse> getAllByCustomerId(Long id);

    List<OrderAuctionResponse> getAllByAuctionProductId(Long id);

    OrderAuctionResponse getById(Long id);

    OrderAuctionResponse create(OrderAuctionRequest request);

    OrderAuctionResponse update(OrderAuctionRequest request, Long id);

    void delete(Long id);
}
