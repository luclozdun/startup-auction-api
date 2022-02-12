package com.auction.auction.shop_bonus.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OrderBonusResponse {
    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;
    private Double total;
    private List<OrderByIdResponse> orderDetailBonus;
}
