package com.auction.auction.shop_unit.dto;

import com.auction.auction.product.dto.CategoryResponse;

import lombok.Data;

@Data
public class ProductUnitResponse {
    private Long id;
    private Boolean avaible;
    private String name;
    private String caracteristic;
    private String image1;
    private String image2;
    private String image3;
    private String video;
    private CategoryResponse category;
    private Long minStock;
    private Long stock;
    private Double price;
}
