package com.auction.auction.shop_bonus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetailBonusIdRequest {
    private Long productId;
    private Long quantify;
}
