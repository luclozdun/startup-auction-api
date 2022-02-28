package com.auction.auction.shop_auction.service;

import java.util.List;

import com.auction.auction.shop_auction.dto.AuctionRequest;
import com.auction.auction.shop_auction.dto.AuctionResponse;

public interface AuctionService {
    List<AuctionResponse> getAll();

    AuctionResponse getById(Long id);

    AuctionResponse create(AuctionRequest request);

    AuctionResponse update(AuctionRequest request, Long id);

    void delete(Long id);
}
