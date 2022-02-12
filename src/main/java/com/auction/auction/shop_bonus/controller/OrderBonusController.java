package com.auction.auction.shop_bonus.controller;

import java.util.List;

import javax.validation.Valid;

import com.auction.auction.shop_bonus.dto.OrderBonusRequest;
import com.auction.auction.shop_bonus.dto.OrderBonusResponse;
import com.auction.auction.shop_bonus.service.OrderBonusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/orders-bonus")
public class OrderBonusController {

    @Autowired
    private OrderBonusService orderBonusService;

    @GetMapping
    private ResponseEntity<List<OrderBonusResponse>> getAll() {
        var ordersBonus = orderBonusService.getAll();
        return new ResponseEntity<>(ordersBonus, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<OrderBonusResponse> getById(@Valid @PathVariable(name = "id") Long id) {
        var orderBonus = orderBonusService.getById(id);
        return new ResponseEntity<>(orderBonus, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<OrderBonusResponse> create(@Valid @RequestBody OrderBonusRequest request) {
        var orderBonus = orderBonusService.create(request);
        return new ResponseEntity<>(orderBonus, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<OrderBonusResponse> update(@Valid @PathVariable(name = "id") Long id,
            @Valid @RequestBody OrderBonusRequest request) {
        var orderBonus = orderBonusService.update(id, request);
        return new ResponseEntity<>(orderBonus, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<OrderBonusResponse> delete(@Valid @PathVariable(name = "id") Long id) {
        orderBonusService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
