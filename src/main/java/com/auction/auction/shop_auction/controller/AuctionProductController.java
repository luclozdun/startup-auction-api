package com.auction.auction.shop_auction.controller;

import java.util.List;

import com.auction.auction.shop_auction.dto.AuctionProductRequest;
import com.auction.auction.shop_auction.dto.AuctionProductResponse;
import com.auction.auction.shop_auction.service.AuctionProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auctionproducts")
public class AuctionProductController {

    @Autowired
    private AuctionProductService auctionProductService;

    @GetMapping
    private ResponseEntity<List<AuctionProductResponse>> getAll() {
        var response = auctionProductService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<AuctionProductResponse> getById(@PathVariable(name = "id") Long id) {
        var response = auctionProductService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<AuctionProductResponse> create(@RequestBody AuctionProductRequest request) {
        var response = auctionProductService.create(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<AuctionProductResponse> update(@PathVariable(name = "id") Long id,
            @RequestBody AuctionProductRequest request) {
        var response = auctionProductService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        auctionProductService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
