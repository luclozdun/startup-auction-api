package com.auction.auction.shop_auction.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.auction.auction.security.model.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_auctions")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderAuction {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private AuctionProduct auctionProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @Column(name = "price")
    private Double price;

    @Column(name = "created_date")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date createdAt;
}