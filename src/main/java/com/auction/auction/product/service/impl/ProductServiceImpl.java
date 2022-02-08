package com.auction.auction.product.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.product.dto.ProductRequest;
import com.auction.auction.product.dto.ProductResponse;
import com.auction.auction.product.model.Product;
import com.auction.auction.product.repository.CategoryRepository;
import com.auction.auction.product.repository.ProductRepository;
import com.auction.auction.product.service.ProductService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<ProductResponse> getAll() {
        var products = productRepository.findAll();
        var responses = products.stream().map(product -> mapper.map(
                product, ProductResponse.class))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public ProductResponse getById(Long id) {
        try {
            var product = productRepository.getProductById(id)
                    .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Product not found"));
            var response = mapper.map(product, ProductResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while getting product");
        }
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        var category = categoryRepository.getCategoryById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Category not found"));

        var entity = new Product();
        entity.setCaracteristic(request.getCaracteristic());
        entity.setCategory(category);
        entity.setImage1(request.getImage1());
        entity.setImage2(request.getImage2());
        entity.setImage3(request.getImage3());
        entity.setVideo(request.getVideo());

        try {
            productRepository.save(entity);
            var response = mapper.map(entity, ProductResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while saving product");
        }

    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        var product = productRepository.getProductById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Product not found"));
        var category = categoryRepository.getCategoryById(request.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundExceptionRequest("Category not found"));
        product.setCaracteristic(request.getCaracteristic());
        product.setCategory(category);
        product.setImage1(request.getImage1());
        product.setImage2(request.getImage2());
        product.setImage3(request.getImage3());
        product.setVideo(request.getVideo());
        try {
            productRepository.save(product);
            var response = mapper.map(product, ProductResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating product");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting product");
        }
    }

    @Override
    public List<ProductResponse> getAllByCategoryId(Long id) {
        var products = productRepository.getProductsByCategory_Id(id);
        var response = products.stream().map(product -> mapper.map(
                product, ProductResponse.class))
                .collect(Collectors.toList());
        return response;

    }
}