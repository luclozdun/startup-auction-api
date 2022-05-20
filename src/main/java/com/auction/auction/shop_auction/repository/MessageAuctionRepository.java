package com.auction.auction.shop_auction.repository;

import java.util.List;
import java.util.Optional;

import com.auction.auction.shop_auction.model.MessageAuction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MessageAuctionRepository extends JpaRepository<MessageAuction, Long> {
    Optional<MessageAuction> getMessageAuctionById(Long id);

    List<MessageAuction> getMessageAuctionsByAuctionId(Long auctionId);

    @Transactional
    @Modifying
    @Query("delete from MessageAuction o where o.auction.id = :auctionId")
    void deleteAllByAuctionId(@Param("auctionId") Long auctionId);
}
