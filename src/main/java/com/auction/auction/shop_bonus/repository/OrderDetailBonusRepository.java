package com.auction.auction.shop_bonus.repository;

import java.util.List;

import com.auction.auction.shop_bonus.model.OrderDetailBonus;
import com.auction.auction.shop_bonus.model.OrderDetailBonusId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailBonusRepository extends JpaRepository<OrderDetailBonus, OrderDetailBonusId> {
    @Query("select o from OrderDetailBonus o where o.orderBonusId.id = :orderId")
    List<OrderDetailBonus> getasd(@Param("orderId") Long orderId);
}
