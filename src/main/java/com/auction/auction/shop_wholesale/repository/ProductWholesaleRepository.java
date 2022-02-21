package com.auction.auction.shop_wholesale.repository;

import java.util.Optional;

import com.auction.auction.shop_wholesale.model.ProductWholesale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductWholesaleRepository extends JpaRepository<ProductWholesale, Long> {
    Optional<ProductWholesale> getProductWholesaleById(Long id);
}
