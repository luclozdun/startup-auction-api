package com.auction.auction.shop_wholesale.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.auction.auction.product.model.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "product_wholesales")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductWholesale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "avaible")
    private Boolean avaible;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "caracteristic")
    private String caracteristic;

    @Column(name = "image_1")
    private String image1;

    @Column(name = "image_2")
    private String image2;

    @Column(name = "image_3")
    private String image3;

    @Column(name = "video")
    private String video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "unid")
    private Long unid;

    @Column(name = "stock")
    private Long stock;

    @Column(name = "price")
    private Double price;
}
