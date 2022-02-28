package com.auction.auction.shop_auction.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AuctionRequest {
    private Boolean avaible;
    private String name;
    private String caracteristic;
    private String image1;
    private String image2;
    private String image3;
    private String video;
    private Long categoryId;
    private Double price;
    private Long customerId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date lastDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createDate;
}
