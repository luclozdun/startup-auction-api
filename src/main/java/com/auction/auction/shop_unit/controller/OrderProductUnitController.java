package com.auction.auction.shop_unit.controller;

import java.util.List;

import javax.validation.Valid;

import com.auction.auction.shop_unit.dto.OrderProductUnitRequest;
import com.auction.auction.shop_unit.dto.OrderProductUnitResponse;
import com.auction.auction.shop_unit.service.OrderProductUnitService;

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
@RequestMapping("/order-products-units")
public class OrderProductUnitController {

    @Autowired
    private OrderProductUnitService orderProductUnitService;

    @GetMapping
    private ResponseEntity<List<OrderProductUnitResponse>> getAll() {
        var orderProductUnits = orderProductUnitService.getAll();
        return new ResponseEntity<>(orderProductUnits, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<List<OrderProductUnitResponse>> create(@Valid @RequestBody OrderProductUnitRequest request) {
        var orderProductUnits = orderProductUnitService.create(request);
        return new ResponseEntity<>(orderProductUnits, HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    private ResponseEntity<List<OrderProductUnitResponse>> getByOrderId(@Valid @PathVariable(name = "id") Long id) {
        var orderProductUnits = orderProductUnitService.getByOrderId(id);
        return new ResponseEntity<>(orderProductUnits, HttpStatus.OK);
    }

    @DeleteMapping("/order/{id}")
    private ResponseEntity<?> deleteByOrderId(@Valid @PathVariable(name = "id") Long id) {
        orderProductUnitService.deleteByOrderId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
