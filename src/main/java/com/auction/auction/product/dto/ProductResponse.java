package com.auction.auction.product.dto;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private CategoryResponse category;
    private String caracteristic;
    private String image1;
    private String image2;
    private String image3;
    private String video;
}
