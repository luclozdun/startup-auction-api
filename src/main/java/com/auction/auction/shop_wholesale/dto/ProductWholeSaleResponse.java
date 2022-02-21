package com.auction.auction.shop_wholesale.dto;

import com.auction.auction.product.dto.CategoryResponse;

import lombok.Data;

@Data
public class ProductWholeSaleResponse {
    private Long id;
    private Boolean avaible;
    private String name;
    private String caracteristic;
    private String image1;
    private String image2;
    private String image3;
    private String video;
    private CategoryResponse category;
    private Long unid;
    private Long stock;
    private Double price;
}
