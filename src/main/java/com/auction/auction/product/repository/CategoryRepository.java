package com.auction.auction.product.repository;

import java.util.Optional;

import com.auction.auction.product.model.Category;
import com.auction.auction.shop_unit.model.ProductUnit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> getCategoryByName(String name);

    Optional<Category> getCategoryById(Long id);

    Object save(ProductUnit productUnit);
}
