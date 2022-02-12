package com.auction.auction.shop_bonus.dto;

import lombok.Data;

@Data
public class OrderDetailBonusResponse {
    private OrderDetailBonusIdResponse orderProductBonusId;
    private Long quantify;
    private Double subtotal;
}
