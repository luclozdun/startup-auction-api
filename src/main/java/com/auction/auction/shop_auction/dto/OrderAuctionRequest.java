package com.auction.auction.shop_auction.dto;

import lombok.Data;

@Data
public class OrderAuctionRequest {
    private Long id;
    private Long auctionId;
    private Long customerId;
    private Double price;
}
