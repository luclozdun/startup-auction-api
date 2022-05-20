package com.auction.auction.shop_auction.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.shop_auction.dto.AuctionRequest;
import com.auction.auction.shop_auction.dto.AuctionResponse;
import com.auction.auction.shop_auction.model.Auction;
import com.auction.auction.shop_auction.repository.AuctionProductRepository;
import com.auction.auction.shop_auction.repository.AuctionRepository;
import com.auction.auction.shop_auction.repository.MessageAuctionRepository;
import com.auction.auction.shop_auction.service.AuctionService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionProductRepository auctionProductRepository;

    @Autowired
    private MessageAuctionRepository messageAuctionRepository;

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
    @Transactional
    public AuctionResponse create(AuctionRequest request) {
        var auctionProduct = auctionProductRepository.getAuctionProductById(request.getAuctionProductId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Auction product not found"));

        var stock = auctionProduct.getStock() - 1;

        if (stock < 0) {
            throw new ResourceNotFoundExceptionRequest("Auction product not stock");
        }
        auctionProduct.setStock(stock);

        var entity = new Auction();
        entity.setActive(true);
        entity.setAuctionProduct(auctionProduct);
        entity.setCreatedAt(request.getCreatedAt());
        entity.setFinishedAt(request.getFinishedAt());
        entity.setPrice(auctionProduct.getPrice());

        try {
            auctionProductRepository.save(auctionProduct);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating auction");
        }

        try {
            auctionRepository.save(entity);
            var response = mapper.map(entity, AuctionResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating auction");
        }
    }

    @Override
    public AuctionResponse update(AuctionRequest request, Long id) {
        var entity = auctionRepository.getAuctionById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Auction not found"));

        var auctionProduct = auctionProductRepository.getAuctionProductById(request.getAuctionProductId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Auction product not found"));

        entity.setActive(true);
        entity.setAuctionProduct(auctionProduct);
        entity.setCreatedAt(new Date());
        entity.setFinishedAt(request.getFinishedAt());

        try {
            auctionRepository.save(entity);
            var response = mapper.map(entity, AuctionResponse.class);
            return response;
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while updating auction");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var entity = auctionRepository.getAuctionById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Auction not found"));

        if (entity.getCustomer() == null) {
            var stock = entity.getAuctionProduct().getStock() + 1;
            entity.getAuctionProduct().setStock(stock);

            try {
                auctionProductRepository.save(entity.getAuctionProduct());
            } catch (Exception e) {
                throw new ResourceNotFoundExceptionRequest("Error ocurred while updating auction product");
            }

            try {
                messageAuctionRepository.deleteAllByAuctionId(id);
            } catch (Exception e) {
                throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting messages auction");
            }

            try {
                auctionRepository.deleteById(id);
            } catch (Exception e) {
                throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting auction");
            }
        } else {
            try {
                messageAuctionRepository.deleteAllByAuctionId(id);
            } catch (Exception e) {
                throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting messages auction");
            }

            try {
                auctionRepository.deleteById(id);
            } catch (Exception e) {
                throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting auction");
            }
        }
    }
}
