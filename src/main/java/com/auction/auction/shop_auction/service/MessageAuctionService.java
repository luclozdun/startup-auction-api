package com.auction.auction.shop_auction.service;

import java.util.List;

import com.auction.auction.shop_auction.dto.AuctionResponse;
import com.auction.auction.shop_auction.dto.MessageAuctionRequest;
import com.auction.auction.shop_auction.dto.MessageAuctionResponse;

public interface MessageAuctionService {
    List<MessageAuctionResponse> getAll();

    List<MessageAuctionResponse> getMessagesByAuctionId(Long id);

    MessageAuctionResponse getById(Long id);

    AuctionResponse create(MessageAuctionRequest request);

    MessageAuctionResponse update(MessageAuctionRequest request, Long id);

    void delete(Long id);
}
