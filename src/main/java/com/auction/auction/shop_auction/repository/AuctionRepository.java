package com.auction.auction.shop_auction.repository;

import java.util.Optional;

import com.auction.auction.shop_auction.model.Auction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Optional<Auction> getAuctionById(Long id);
}
