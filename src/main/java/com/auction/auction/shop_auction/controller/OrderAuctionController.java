package com.auction.auction.shop_auction.controller;

import java.util.List;

import com.auction.auction.shop_auction.dto.OrderAuctionRequest;
import com.auction.auction.shop_auction.dto.OrderAuctionResponse;
import com.auction.auction.shop_auction.service.OrderAuctionService;

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
@RequestMapping("/orderauctions")
public class OrderAuctionController {

    @Autowired
    private OrderAuctionService auctionService;

    @GetMapping
    private ResponseEntity<List<OrderAuctionResponse>> getAll() {
        var response = auctionService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    private ResponseEntity<List<OrderAuctionResponse>> getAllByCustomerId(@PathVariable(name = "id") Long id) {
        var response = auctionService.getAllByCustomerId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/auctionproduct/{id}")
    private ResponseEntity<List<OrderAuctionResponse>> getAllByAuctionProductId(@PathVariable(name = "id") Long id) {
        var response = auctionService.getAllByAuctionProductId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<OrderAuctionResponse> getById(@PathVariable(name = "id") Long id) {
        var response = auctionService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<OrderAuctionResponse> create(@RequestBody OrderAuctionRequest request) {
        var response = auctionService.create(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<OrderAuctionResponse> update(@PathVariable(name = "id") Long id,
            @RequestBody OrderAuctionRequest request) {
        var response = auctionService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        auctionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
