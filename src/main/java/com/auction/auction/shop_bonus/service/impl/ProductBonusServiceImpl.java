package com.auction.auction.shop_bonus.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.product.repository.ProductRepository;
import com.auction.auction.shop_bonus.dto.ProductBonusRequest;
import com.auction.auction.shop_bonus.dto.ProductBonusResponse;
import com.auction.auction.shop_bonus.model.ProductBonus;
import com.auction.auction.shop_bonus.repository.ProductBonusRepository;
import com.auction.auction.shop_bonus.service.ProductBonusService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductBonusServiceImpl implements ProductBonusService {

    @Autowired
    private ProductBonusRepository bonusRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<ProductBonusResponse> getAll() {
        var productsBonus = bonusRepository.findAll();
        var response = productsBonus.stream().map(productBonus -> mapper.map(productBonus, ProductBonusResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public ProductBonusResponse getById(Long id) {
        var productBonus = bonusRepository.getProductBonusById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Product bonus not found by id"));
        var response = mapper.map(productBonus, ProductBonusResponse.class);
        return response;
    }

    @Override
    public ProductBonusResponse create(ProductBonusRequest request) {
        var product = productRepository.getProductById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Product not found by id"));
        var productBonus = new ProductBonus();
        productBonus.setPrice(request.getPrice());
        productBonus.setProduct(product);
        productBonus.setStartDate(request.getStartDate());
        productBonus.setStock(request.getStock());
        try {
            bonusRepository.save(productBonus);
            var response = mapper.map(productBonus, ProductBonusResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating product bonus");
        }
    }

    @Override
    public ProductBonusResponse update(Long id, ProductBonusRequest request) {
        var product = productRepository.getProductById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Product not found by id"));
        var productBonus = bonusRepository.getProductBonusById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Product Bonus not found by id"));
        productBonus.setPrice(request.getPrice());
        productBonus.setProduct(product);
        productBonus.setStartDate(request.getStartDate());
        productBonus.setStock(request.getStock());
        try {
            bonusRepository.save(productBonus);
            var response = mapper.map(productBonus, ProductBonusResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating product bonus");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            bonusRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting product bonus");
        }
    }

}
