package com.auction.auction.shop_wholesale.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OrderProductWholesaleRequest {
    List<OrderProductQuantifyRequest> orderProductQuantifyRequests;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created;
}
