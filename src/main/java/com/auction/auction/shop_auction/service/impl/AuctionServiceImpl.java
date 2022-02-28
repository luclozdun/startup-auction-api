package com.auction.auction.shop_auction.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.product.repository.CategoryRepository;
import com.auction.auction.shop_auction.dto.AuctionRequest;
import com.auction.auction.shop_auction.dto.AuctionResponse;
import com.auction.auction.shop_auction.model.Auction;
import com.auction.auction.shop_auction.repository.AuctionRepository;
import com.auction.auction.shop_auction.service.AuctionService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<AuctionResponse> getAll() {
        var auctions = auctionRepository.findAll();
        var response = auctions.stream().map(auction -> mapper.map(auction, AuctionResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public AuctionResponse getById(Long id) {
        var auction = auctionRepository.getAuctionById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Auction not found by id"));
        var response = mapper.map(auction, AuctionResponse.class);
        return response;
    }

    @Override
    public AuctionResponse create(AuctionRequest request) {
        var category = categoryRepository.getCategoryById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Category not found"));

        var auction = new Auction();
        auction.setAvaible(true);
        auction.setCaracteristic(request.getCaracteristic());
        auction.setCategory(category);
        auction.setCreateDate(request.getCreateDate());
        auction.setCustomer(null);
        auction.setImage1(request.getImage1());
        auction.setImage2(request.getImage2());
        auction.setImage3(request.getImage3());
        auction.setLastDate(request.getLastDate());
        auction.setName(request.getName());
        auction.setPrice(request.getPrice());
        auction.setVideo(request.getVideo());
        auction.setPriceBase(request.getPrice());

        try {
            auctionRepository.save(auction);
            var response = mapper.map(auction, AuctionResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating auction");
        }
    }

    @Override
    public AuctionResponse update(AuctionRequest request, Long id) {
        var category = categoryRepository.getCategoryById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Category not found"));

        var auction = auctionRepository.getAuctionById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Auction not found"));

        auction.setAvaible(true);
        auction.setCaracteristic(request.getCaracteristic());
        auction.setCategory(category);
        auction.setCreateDate(request.getCreateDate());
        auction.setCustomer(null);
        auction.setImage1(request.getImage1());
        auction.setImage2(request.getImage2());
        auction.setImage3(request.getImage3());
        auction.setLastDate(request.getLastDate());
        auction.setName(request.getName());
        auction.setPrice(request.getPrice());
        auction.setVideo(request.getVideo());
        auction.setPriceBase(request.getPrice());

        try {
            auctionRepository.save(auction);
            var response = mapper.map(auction, AuctionResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating auction");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            auctionRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating auction");
        }
    }
}
