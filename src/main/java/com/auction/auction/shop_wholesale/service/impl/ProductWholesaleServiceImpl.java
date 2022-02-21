package com.auction.auction.shop_wholesale.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.product.repository.CategoryRepository;
import com.auction.auction.shop_wholesale.dto.ProductWholeSaleResponse;
import com.auction.auction.shop_wholesale.dto.ProductWholesaleRequest;
import com.auction.auction.shop_wholesale.model.ProductWholesale;
import com.auction.auction.shop_wholesale.repository.ProductWholesaleRepository;
import com.auction.auction.shop_wholesale.service.ProductWholesaleService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductWholesaleServiceImpl implements ProductWholesaleService {

    @Autowired
    private ProductWholesaleRepository productWholesaleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<ProductWholeSaleResponse> getAll() {
        var productWholesales = productWholesaleRepository.findAll();
        var response = productWholesales.stream()
                .map(productWholesale -> mapper.map(productWholesale, ProductWholeSaleResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public ProductWholeSaleResponse getById(Long id) {
        var productWholesale = productWholesaleRepository.getProductWholesaleById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Productwholesale not found"));
        var response = mapper.map(productWholesale, ProductWholeSaleResponse.class);
        return response;
    }

    @Override
    public ProductWholeSaleResponse create(ProductWholesaleRequest request) {
        var category = categoryRepository.getCategoryById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Category not found"));
        var productWholesale = new ProductWholesale();
        productWholesale.setAvaible(true);
        productWholesale.setCaracteristic(request.getCaracteristic());
        productWholesale.setCategory(category);
        productWholesale.setImage1(request.getImage1());
        productWholesale.setImage2(request.getImage2());
        productWholesale.setImage3(request.getImage3());
        productWholesale.setName(request.getName());
        productWholesale.setStock(request.getStock());
        productWholesale.setUnid(request.getUnid());
        productWholesale.setVideo(request.getVideo());
        productWholesale.setPrice(request.getPrice());

        try {
            productWholesaleRepository.save(productWholesale);
            var response = mapper.map(productWholesale, ProductWholeSaleResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating Product wholesale");
        }
    }

    @Override
    public ProductWholeSaleResponse update(ProductWholesaleRequest request, Long id) {
        var category = categoryRepository.getCategoryById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Category not found"));
        var productWholesale = productWholesaleRepository.getProductWholesaleById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Product Wholesale not found"));
        productWholesale.setAvaible(true);
        productWholesale.setCaracteristic(request.getCaracteristic());
        productWholesale.setCategory(category);
        productWholesale.setImage1(request.getImage1());
        productWholesale.setImage2(request.getImage2());
        productWholesale.setImage3(request.getImage3());
        productWholesale.setName(request.getName());
        productWholesale.setStock(request.getStock());
        productWholesale.setUnid(request.getUnid());
        productWholesale.setVideo(request.getVideo());
        productWholesale.setPrice(request.getPrice());

        try {
            productWholesaleRepository.save(productWholesale);
            var response = mapper.map(productWholesale, ProductWholeSaleResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating Product wholesale");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            productWholesaleRepository.deleteById(id);
            ;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting Product wholesale");
        }
    }

}
