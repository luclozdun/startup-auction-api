package com.auction.auction.shop_unit.dto;

import lombok.Data;

@Data
public class OrderProductUnitResponse {
    private OrderProductUnitId orderProductId;
    private Long quantify;
}