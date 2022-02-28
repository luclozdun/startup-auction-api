package com.auction.auction.shop_auction.repository;

import java.util.List;
import java.util.Optional;

import com.auction.auction.shop_auction.model.MessageAuction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageAuctionRepository extends JpaRepository<MessageAuction, Long> {
    Optional<MessageAuction> getMessageAuctionById(Long id);

    List<MessageAuction> getMessageAuctionsByAuctionId(Long auctionId);
}
