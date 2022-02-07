package com.auction.auction.product.service;

import java.util.List;

import com.auction.auction.product.dto.ProductRequest;
import com.auction.auction.product.dto.ProductResponse;

public interface ProductService {
    List<ProductResponse> getAll();

    ProductResponse getById(Long id);

    ProductResponse create(ProductRequest request);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);

    List<ProductResponse> getAllByCategoryId(Long id);
}
