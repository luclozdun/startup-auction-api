package com.auction.auction.shop_bonus.controller;

import java.util.List;

import javax.validation.Valid;

import com.auction.auction.shop_bonus.dto.OrderDetailBonusRequest;
import com.auction.auction.shop_bonus.dto.OrderDetailBonusResponse;
import com.auction.auction.shop_bonus.service.OrderDetailBonusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders-details-bonus")
public class OrderDetailBonusController {

    @Autowired
    private OrderDetailBonusService orderDetailBonusService;

    @GetMapping
    private ResponseEntity<List<OrderDetailBonusResponse>> getAll() {
        var orderDetailsBonus = orderDetailBonusService.getAll();
        return new ResponseEntity<>(orderDetailsBonus, HttpStatus.OK);
    }

    @PostMapping("/asdd")
    public ResponseEntity<List<OrderDetailBonusResponse>> createe(
            @Valid @RequestBody OrderDetailBonusRequest request) {
        var orderDetailBonus = orderDetailBonusService.create(request);
        return new ResponseEntity<>(orderDetailBonus, HttpStatus.OK);
    }
}
