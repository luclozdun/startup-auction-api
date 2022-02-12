package com.auction.auction.shop_bonus.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_details_bonus")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDetailBonus {
    @EmbeddedId
    private OrderDetailBonusId orderProductBonusId = new OrderDetailBonusId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderBonusId")
    @JoinColumn(name = "order_bonus_id")
    private OrderBonus orderBonusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productBonusId")
    @JoinColumn(name = "product_bonus_id")
    private ProductBonus productBonusId;

    @Column(name = "quantify")
    private Long quantify;

    @Column(name = "subtotal")
    private Double subtotal;
}