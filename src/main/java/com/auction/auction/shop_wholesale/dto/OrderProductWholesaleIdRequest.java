package com.auction.auction.shop_wholesale.dto;

import lombok.Data;

@Data
public class OrderProductWholesaleIdRequest {
    private Long orderWholesaleId;
    private Long productWholesaleId;
}
