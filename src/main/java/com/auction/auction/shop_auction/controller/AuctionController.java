package com.auction.auction.shop_auction.controller;

import java.util.List;

import javax.validation.Valid;

import com.auction.auction.shop_auction.dto.AuctionRequest;
import com.auction.auction.shop_auction.dto.AuctionResponse;
import com.auction.auction.shop_auction.service.AuctionService;

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
@RequestMapping("/auctions")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @GetMapping
    private ResponseEntity<List<AuctionResponse>> getAll() {
        var auctions = auctionService.getAll();
        return new ResponseEntity<>(auctions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<AuctionResponse> getById(@Valid @PathVariable(name = "id") Long id) {
        var auction = auctionService.getById(id);
        return new ResponseEntity<>(auction, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<AuctionResponse> create(@Valid @RequestBody AuctionRequest request) {
        var auction = auctionService.create(request);
        return new ResponseEntity<>(auction, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<AuctionResponse> update(@Valid @PathVariable(name = "id") Long id,
            @Valid @RequestBody AuctionRequest request) {
        var auction = auctionService.update(request, id);
        return new ResponseEntity<>(auction, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@Valid @PathVariable(name = "id") Long id) {
        auctionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
