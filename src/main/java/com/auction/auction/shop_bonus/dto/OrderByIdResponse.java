package com.auction.auction.shop_bonus.dto;

import lombok.Data;

@Data
public class OrderByIdResponse {
    private ProductBonusResponse productBonusId;
    private Long quantify;
    private Double subtotal;
}
