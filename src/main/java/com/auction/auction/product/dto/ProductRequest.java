package com.auction.auction.product.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private Long categoryId;
    private String caracteristic;
    private String image1;
    private String image2;
    private String image3;
    private String video;
}
