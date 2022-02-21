package com.auction.auction.shop_wholesale.dto;

import lombok.Data;

@Data
public class OrderProductQuantifyRequest {
    private Long quantify;
    private Long productId;
}
