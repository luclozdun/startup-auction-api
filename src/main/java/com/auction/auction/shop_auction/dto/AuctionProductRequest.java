package com.auction.auction.shop_auction.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AuctionProductRequest {
    private String name;
    private String caracteristic;
    private String image1;
    private String image2;
    private String image3;
    private String video;
    private Long categoryId;
    private Long price;
    private Long stock;
    private Long employeeId;
    @JsonFormat(pattern = "yyyy/MM/dd", shape = JsonFormat.Shape.STRING)
    private Date updatedAt;
}
