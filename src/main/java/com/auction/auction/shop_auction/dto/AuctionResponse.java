package com.auction.auction.shop_auction.dto;

import java.util.Date;

import com.auction.auction.product.dto.CategoryResponse;
import com.auction.auction.security.dto.CustomerResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AuctionResponse {
    private Long id;
    private Boolean avaible;
    private String name;
    private String caracteristic;
    private String image1;
    private String image2;
    private String image3;
    private String video;
    private CategoryResponse category;
    private Double price;
    private CustomerResponse customer;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date lastDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createDate;
}
