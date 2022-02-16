package com.auction.auction.shop_unit.model;

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
@Table(name = "orders_product_unit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductUnit {
    @EmbeddedId
    private OrderProductUnitId orderProductId = new OrderProductUnitId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderUnitId")
    @JoinColumn(name = "order_unit_id")
    private OrderUnit orderUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productUnitId")
    @JoinColumn(name = "product_unit_id")
    private ProductUnit productUnit;

    @Column(name = "quantify")
    private Long quantify;
}
