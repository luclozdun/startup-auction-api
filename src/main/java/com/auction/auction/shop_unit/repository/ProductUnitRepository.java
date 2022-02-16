package com.auction.auction.shop_unit.repository;

import java.util.Optional;

import com.auction.auction.shop_unit.model.ProductUnit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductUnitRepository extends JpaRepository<ProductUnit, Long> {
    Optional<ProductUnit> getProductUnitById(Long id);
}
