package com.auction.auction.shop_bonus.dto;

import java.util.Date;

import com.auction.auction.product.dto.ProductResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ProductBonusResponse {
    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date startDate;
    private Double price;
    private Long stock;
    private ProductResponse product;
}
