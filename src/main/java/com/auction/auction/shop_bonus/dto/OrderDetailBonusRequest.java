package com.auction.auction.shop_bonus.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class OrderDetailBonusRequest {
    private Long customerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date date;
    private List<OrderDetailBonusIdRequest> products;
}
