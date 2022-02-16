package com.auction.auction.shop_unit.repository;

import java.util.Optional;

import com.auction.auction.shop_unit.model.OrderUnit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderUnitRepository extends JpaRepository<OrderUnit, Long> {
    Optional<OrderUnit> getOrderUnitById(Long id);
}
