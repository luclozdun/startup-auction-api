package com.auction.auction.shop_auction.repository;

import java.util.Optional;

import com.auction.auction.shop_auction.model.AuctionProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionProductRepository extends JpaRepository<AuctionProduct, Long> {
    Optional<AuctionProduct> getAuctionProductById(Long id);
}
