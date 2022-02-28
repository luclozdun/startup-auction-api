package com.auction.auction.shop_auction.dto;

import lombok.Data;

@Data
public class AuctionPriceRequest {
    private Double price;
    private Long customerId;
}
