package com.auction.auction.shop_bonus.controller;

import java.util.List;

import javax.validation.Valid;

import com.auction.auction.shop_bonus.dto.ProductBonusRequest;
import com.auction.auction.shop_bonus.dto.ProductBonusResponse;
import com.auction.auction.shop_bonus.service.ProductBonusService;

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
@RequestMapping("/products-bonus")
public class ProductBonusController {

    @Autowired
    private ProductBonusService productBonusService;

    @GetMapping
    private ResponseEntity<List<ProductBonusResponse>> getAll() {
        var productsBonus = productBonusService.getAll();
        return new ResponseEntity<>(productsBonus, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ProductBonusResponse> getById(@Valid @PathVariable(name = "id") Long id) {
        var productBonus = productBonusService.getById(id);
        return new ResponseEntity<>(productBonus, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<ProductBonusResponse> create(@Valid @RequestBody ProductBonusRequest request) {
        var productBonus = productBonusService.create(request);
        return new ResponseEntity<>(productBonus, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<ProductBonusResponse> update(@Valid @PathVariable(name = "id") Long id,
            @Valid @RequestBody ProductBonusRequest request) {
        var productBonus = productBonusService.update(id, request);
        return new ResponseEntity<>(productBonus, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@Valid @PathVariable(name = "id") Long id) {
        productBonusService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
