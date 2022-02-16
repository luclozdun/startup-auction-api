package com.auction.auction.shop_unit.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.product.repository.CategoryRepository;
import com.auction.auction.shop_unit.dto.ProductUnitRequest;
import com.auction.auction.shop_unit.dto.ProductUnitResponse;
import com.auction.auction.shop_unit.model.ProductUnit;
import com.auction.auction.shop_unit.repository.ProductUnitRepository;
import com.auction.auction.shop_unit.service.ProductUnitService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductUnitServiceImpl implements ProductUnitService {

    @Autowired
    private ProductUnitRepository productUnitRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<ProductUnitResponse> getAll() {
        var productsUnit = productUnitRepository.findAll();
        var response = productsUnit.stream().map(productUnit -> mapper.map(productUnit, ProductUnitResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public ProductUnitResponse getById(Long id) {
        var productUnit = productUnitRepository.getProductUnitById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Product unit not found"));
        var response = mapper.map(productUnit, ProductUnitResponse.class);
        return response;
    }

    @Override
    public ProductUnitResponse create(ProductUnitRequest request) {
        var category = categoryRepository.getCategoryById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Category not found"));

        var productUnit = new ProductUnit();
        productUnit.setAvaible(request.getAvaible());
        productUnit.setCaracteristic(request.getCaracteristic());
        productUnit.setCategory(category);
        productUnit.setImage1(request.getImage1());
        productUnit.setImage2(request.getImage2());
        productUnit.setImage3(request.getImage3());
        productUnit.setVideo(request.getVideo());
        productUnit.setMinStock(0L);
        productUnit.setName(request.getName());
        productUnit.setPrice(request.getPrice());
        productUnit.setStock(request.getStock());

        try {
            productUnitRepository.save(productUnit);
            var response = mapper.map(productUnit, ProductUnitResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating product unit");
        }
    }

    @Override
    public ProductUnitResponse update(Long id, ProductUnitRequest request) {
        var category = categoryRepository.getCategoryById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Category not found"));

        var productUnit = productUnitRepository.getProductUnitById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Product unit not found"));
        productUnit.setAvaible(request.getAvaible());
        productUnit.setCaracteristic(request.getCaracteristic());
        productUnit.setCategory(category);
        productUnit.setImage1(request.getImage1());
        productUnit.setImage2(request.getImage2());
        productUnit.setImage3(request.getImage3());
        productUnit.setVideo(request.getVideo());
        productUnit.setMinStock(0L);
        productUnit.setName(request.getName());
        productUnit.setPrice(request.getPrice());
        productUnit.setStock(request.getStock());

        try {
            productUnitRepository.save(productUnit);
            var response = mapper.map(productUnit, ProductUnitResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating product unit");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            productUnitRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting product unit");
        }
    }

}
