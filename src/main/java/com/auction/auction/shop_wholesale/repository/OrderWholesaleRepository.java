package com.auction.auction.shop_wholesale.repository;

import java.util.Optional;

import com.auction.auction.shop_wholesale.model.OrderWholesale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderWholesaleRepository extends JpaRepository<OrderWholesale, Long> {
    Optional<OrderWholesale> getOrderWholesaleById(Long id);
}
