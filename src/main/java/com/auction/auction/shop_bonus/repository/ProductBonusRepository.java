package com.auction.auction.shop_bonus.repository;

import java.util.Optional;

import com.auction.auction.shop_bonus.model.ProductBonus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBonusRepository extends JpaRepository<ProductBonus, Long> {
    Optional<ProductBonus> getProductBonusById(Long id);
}
