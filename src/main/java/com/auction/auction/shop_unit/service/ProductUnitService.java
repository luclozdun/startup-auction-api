package com.auction.auction.shop_unit.service;

import java.util.List;

import com.auction.auction.shop_unit.dto.ProductUnitRequest;
import com.auction.auction.shop_unit.dto.ProductUnitResponse;

public interface ProductUnitService {
    List<ProductUnitResponse> getAll();

    ProductUnitResponse getById(Long id);

    ProductUnitResponse create(ProductUnitRequest request);

    ProductUnitResponse update(Long id, ProductUnitRequest request);

    void delete(Long id);
}
