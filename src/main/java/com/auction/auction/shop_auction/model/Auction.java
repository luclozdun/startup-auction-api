package com.auction.auction.shop_auction.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.auction.auction.security.model.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_auctions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_product_id")
    private AuctionProduct auctionProduct;

    @Column(name = "price")
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @Column(name = "created_date")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date createdAt;

    @Column(name = "finished_date")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date finishedAt;

    private Boolean active;
}
