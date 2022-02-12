package com.auction.auction.shop_bonus.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OrderBonusRequest {
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;
    private Double total;
    private Long customerId;
}
