package com.auction.auction.product.service;

import java.util.List;

import com.auction.auction.product.dto.CategoryRequest;
import com.auction.auction.product.dto.CategoryResponse;

public interface CategoryService {
    List<CategoryResponse> getAll();

    CategoryResponse getById(Long id);

    CategoryResponse create(CategoryRequest request);

    CategoryResponse updateById(Long id, CategoryRequest request);

    void deleteById(Long id);
}
