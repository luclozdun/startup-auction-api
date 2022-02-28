package com.auction.auction.shop_auction.dto;

import lombok.Data;

@Data
public class MessageAuctionRequest {
    private Long customerId;
    private Long auctionId;
    private Double price;
}
