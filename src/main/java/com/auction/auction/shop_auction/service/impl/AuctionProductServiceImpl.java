package com.auction.auction.shop_auction.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.product.repository.CategoryRepository;
import com.auction.auction.security.repository.EmployeeRepository;
import com.auction.auction.shop_auction.dto.AuctionProductRequest;
import com.auction.auction.shop_auction.dto.AuctionProductResponse;
import com.auction.auction.shop_auction.model.AuctionProduct;
import com.auction.auction.shop_auction.repository.AuctionProductRepository;
import com.auction.auction.shop_auction.service.AuctionProductService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionProductServiceImpl implements AuctionProductService {

    @Autowired
    private AuctionProductRepository auctionProductRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<AuctionProductResponse> getAll() {
        var entities = auctionProductRepository.findAll();
        var response = entities.stream().map(entity -> mapper.map(entity, AuctionProductResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public AuctionProductResponse getById(Long id) {
        var entity = auctionProductRepository.getAuctionProductById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Auction product not found"));
        var response = mapper.map(entity, AuctionProductResponse.class);
        return response;
    }

    @Override
    public AuctionProductResponse create(AuctionProductRequest request) {
        var category = categoryRepository.getCategoryById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Category not found"));

        var employee = employeeRepository.getEmployeeById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Employee not found"));

        AuctionProduct entity = new AuctionProduct();

        entity.setCaracteristic(request.getCaracteristic());
        entity.setCategory(category);
        entity.setImage1(request.getImage1());
        entity.setImage2(request.getImage2());
        entity.setImage3(request.getImage3());
        entity.setName(request.getName());
        entity.setPrice(request.getPrice());
        entity.setVideo(request.getVideo());
        entity.setEmployee(employee);
        entity.setUpdatedAt(new Date());
        entity.setCreatedAt(new Date());
        entity.setPrice(request.getPrice());

        try {
            auctionProductRepository.save(entity);
            var response = mapper.map(entity, AuctionProductResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating auction product");
        }
    }

    @Override
    public AuctionProductResponse update(AuctionProductRequest request, Long id) {
        var entity = auctionProductRepository.getAuctionProductById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Auction product not found"));

        var category = categoryRepository.getCategoryById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Category not found"));

        var employee = employeeRepository.getEmployeeById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Employee not found"));

        entity.setCaracteristic(request.getCaracteristic());
        entity.setCategory(category);
        entity.setImage1(request.getImage1());
        entity.setImage2(request.getImage2());
        entity.setImage3(request.getImage3());
        entity.setName(request.getName());
        entity.setPrice(request.getPrice());
        entity.setVideo(request.getVideo());
        entity.setEmployee(employee);
        entity.setUpdatedAt(new Date());
        entity.setPrice(request.getPrice());

        try {
            auctionProductRepository.save(entity);
            var response = mapper.map(entity, AuctionProductResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating auction product");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            auctionProductRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting auction product");
        }
    }

}
