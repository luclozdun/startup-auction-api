package com.auction.auction.shop_wholesale.repository;

import java.util.List;

import com.auction.auction.shop_wholesale.model.OrderProductWholesale;
import com.auction.auction.shop_wholesale.model.OrderProductWholesaleId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductWholesaleRepository extends JpaRepository<OrderProductWholesale, OrderProductWholesaleId> {

    @Query("delete from OrderProductWholesale o where o.orderWholesale.id = :orderId")
    void deleteOrderProductWholesaleByOrderId(Long orderId);

    @Query("select o from OrderProductWholesale o where o.orderWholesale.id = :orderId")
    List<OrderProductWholesale> getOrderProductWholesaleByOrderId(Long orderId);
}
