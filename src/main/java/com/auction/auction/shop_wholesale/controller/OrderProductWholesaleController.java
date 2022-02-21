package com.auction.auction.shop_wholesale.controller;

import java.util.List;

import javax.validation.Valid;

import com.auction.auction.shop_wholesale.dto.OrderProductWholesaleRequest;
import com.auction.auction.shop_wholesale.dto.OrderProductWholesaleResponse;
import com.auction.auction.shop_wholesale.service.OrderProductWholesaleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderdetailwholesale")
public class OrderProductWholesaleController {

    @Autowired
    private OrderProductWholesaleService orderProductWholesaleService;

    @GetMapping
    private ResponseEntity<List<OrderProductWholesaleResponse>> getAll() {
        var orderProductWholesales = orderProductWholesaleService.getAll();
        return new ResponseEntity<>(orderProductWholesales, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<List<OrderProductWholesaleResponse>> create(
            @Valid @RequestBody OrderProductWholesaleRequest request) {
        var orderProductWholesales = orderProductWholesaleService.create(request);
        return new ResponseEntity<>(orderProductWholesales, HttpStatus.OK);
    }

    @DeleteMapping("/order/{id}")
    private ResponseEntity<?> deleteByOrderId(@Valid @PathVariable(name = "id") Long id) {
        orderProductWholesaleService.deleteByOrderId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
