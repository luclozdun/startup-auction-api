package com.auction.auction.shop_auction.dto;

import com.auction.auction.security.dto.CustomerSimpleResponse;

import lombok.Data;

@Data
public class OrderAuctionResponse {
    private Long id;
    private AuctionResponse auction;
    private CustomerSimpleResponse customer;
    private Double price;
}
