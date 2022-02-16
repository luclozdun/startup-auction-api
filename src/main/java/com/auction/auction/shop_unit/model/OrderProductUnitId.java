package com.auction.auction.shop_unit.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class OrderProductUnitId implements Serializable {
    private Long orderUnitId;
    private Long productUnitId;
}
