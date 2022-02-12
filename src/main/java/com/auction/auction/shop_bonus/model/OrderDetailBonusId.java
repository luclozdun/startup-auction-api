package com.auction.auction.shop_bonus.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class OrderDetailBonusId implements Serializable {
    @Column(name = "orderBonusId")
    private Long orderBonusId;
    @Column(name = "productBonusId")
    private Long productBonusId;

}
