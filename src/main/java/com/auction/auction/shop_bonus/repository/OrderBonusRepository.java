package com.auction.auction.shop_bonus.repository;

import java.util.Optional;

import com.auction.auction.shop_bonus.model.OrderBonus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBonusRepository extends JpaRepository<OrderBonus, Long> {
    Optional<OrderBonus> getOrderBonusById(Long id);
}
