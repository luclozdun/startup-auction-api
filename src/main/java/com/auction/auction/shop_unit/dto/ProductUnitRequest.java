package com.auction.auction.shop_unit.dto;

import lombok.Data;

@Data
public class ProductUnitRequest {
    private Boolean avaible;
    private String name;
    private String caracteristic;
    private String image1;
    private String image2;
    private String image3;
    private String video;
    private Long categoryId;
    private Long stock;
    private Double price;
}
