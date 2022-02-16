package com.auction.auction.shop_unit.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderProductUnitRequest {
    private List<ProductUnitQuantify> productUnitQuantifies;
    private Date date_created;
}
