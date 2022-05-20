package com.auction.auction.shop_auction.service;

import java.util.List;

import com.auction.auction.shop_auction.dto.AuctionProductRequest;
import com.auction.auction.shop_auction.dto.AuctionProductResponse;

public interface AuctionProductService {
    List<AuctionProductResponse> getAll();

    AuctionProductResponse getById(Long id);

    AuctionProductResponse create(AuctionProductRequest request);

    AuctionProductResponse update(AuctionProductRequest request, Long id);

    void delete(Long id);
}
