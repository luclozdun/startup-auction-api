package com.auction.auction.shop_unit.repository;

import java.util.List;

import com.auction.auction.shop_unit.model.OrderProductUnit;
import com.auction.auction.shop_unit.model.OrderProductUnitId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductUnitRepository extends JpaRepository<OrderProductUnit, OrderProductUnitId> {

    @Query("select o from OrderProductUnit o where o.orderUnit.id = :orderId")
    List<OrderProductUnit> getAllByOrderId(Long orderId);
}
