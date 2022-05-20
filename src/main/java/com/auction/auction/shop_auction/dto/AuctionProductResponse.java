package com.auction.auction.shop_auction.dto;

import java.util.Date;

import com.auction.auction.product.dto.CategoryResponse;
import com.auction.auction.security.dto.EmployeeSimpleResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AuctionProductResponse {
    private Long id;
    private String name;
    private String caracteristic;
    private String image1;
    private String image2;
    private String image3;
    private String video;
    private CategoryResponse category;
    private Long price;
    private Long stock;
    private EmployeeSimpleResponse employee;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date updatedAt;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date createdAt;
}
