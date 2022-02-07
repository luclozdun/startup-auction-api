package com.auction.auction.product.repository;

import java.util.List;
import java.util.Optional;

import com.auction.auction.product.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> getProductById(Long id);

    List<Product> getProductsByCategory_Id(Long id);
}
