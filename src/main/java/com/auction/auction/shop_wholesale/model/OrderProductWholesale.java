package com.auction.auction.shop_wholesale.model;

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

@Table(name = "order_product_wholesales")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderProductWholesale {
    @EmbeddedId
    private OrderProductWholesaleId orderProductWholesaleId = new OrderProductWholesaleId();

    @Column(name = "quantify")
    private Long quantify;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderWholesaleId")
    @JoinColumn(name = "order_wholesale_id")
    private OrderWholesale orderWholesale;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productWholesaleId")
    @JoinColumn(name = "product_wholesale_id")
    private ProductWholesale productWholesale;
}
