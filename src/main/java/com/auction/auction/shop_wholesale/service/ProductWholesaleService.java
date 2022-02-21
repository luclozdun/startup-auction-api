package com.auction.auction.shop_wholesale.service;

import java.util.List;

import com.auction.auction.shop_wholesale.dto.ProductWholeSaleResponse;
import com.auction.auction.shop_wholesale.dto.ProductWholesaleRequest;

public interface ProductWholesaleService {
    List<ProductWholeSaleResponse> getAll();

    ProductWholeSaleResponse getById(Long id);

    ProductWholeSaleResponse create(ProductWholesaleRequest request);

    ProductWholeSaleResponse update(ProductWholesaleRequest request, Long id);

    void delete(Long id);
}
