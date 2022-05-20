package com.auction.auction.shop_auction.dto;

import com.auction.auction.security.dto.CustomerSimpleResponse;

import lombok.Data;

@Data
public class MessageAuctionResponse {
    private Long id;
    private CustomerSimpleResponse customer;
    private AuctionResponse auction;
    private Long price;
}
