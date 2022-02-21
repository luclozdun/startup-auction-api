package com.auction.auction.shop_wholesale.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class OrderProductWholesaleId implements Serializable {
    private Long orderWholesaleId;
    private Long productWholesaleId;
}
