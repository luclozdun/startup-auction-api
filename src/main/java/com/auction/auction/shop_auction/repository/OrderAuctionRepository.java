package com.auction.auction.shop_auction.repository;

import java.util.List;
import java.util.Optional;

import com.auction.auction.shop_auction.model.OrderAuction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAuctionRepository extends JpaRepository<OrderAuction, Long> {
    Optional<OrderAuction> getOrderAuctionById(Long id);

    List<OrderAuction> findAllByCustomerId(Long id);

    List<OrderAuction> findAllByAuctionProductId(Long id);
}
