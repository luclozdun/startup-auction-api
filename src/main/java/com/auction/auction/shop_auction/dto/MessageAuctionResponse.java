package com.auction.auction.shop_auction.dto;

import com.auction.auction.security.dto.CustomerResponse;

import lombok.Data;

@Data
public class MessageAuctionResponse {
    private Long id;
    private CustomerResponse customer;
    private AuctionResponse auction;
    private Double price;
}
