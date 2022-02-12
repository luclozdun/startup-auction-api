package com.auction.auction.shop_bonus.service;

import java.util.List;

import com.auction.auction.shop_bonus.dto.ProductBonusRequest;
import com.auction.auction.shop_bonus.dto.ProductBonusResponse;

public interface ProductBonusService {
    List<ProductBonusResponse> getAll();

    ProductBonusResponse getById(Long id);

    ProductBonusResponse create(ProductBonusRequest request);

    ProductBonusResponse update(Long id, ProductBonusRequest request);

    void delete(Long id);
}
