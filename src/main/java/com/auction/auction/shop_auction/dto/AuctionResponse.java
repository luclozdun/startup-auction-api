package com.auction.auction.shop_auction.dto;

import java.util.Date;

import com.auction.auction.security.dto.CustomerSimpleResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AuctionResponse {
    private Long id;
    private AuctionProductResponse auctionProduct;
    private Long price;
    private CustomerSimpleResponse customer;
    private Boolean active;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date finishedAt;
}
