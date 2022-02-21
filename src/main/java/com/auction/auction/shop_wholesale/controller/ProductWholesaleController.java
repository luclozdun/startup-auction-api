package com.auction.auction.shop_wholesale.controller;

import java.util.List;

import javax.validation.Valid;

import com.auction.auction.shop_wholesale.dto.ProductWholeSaleResponse;
import com.auction.auction.shop_wholesale.dto.ProductWholesaleRequest;
import com.auction.auction.shop_wholesale.service.ProductWholesaleService;

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
@RequestMapping("/productwholesale")
public class ProductWholesaleController {

    @Autowired
    private ProductWholesaleService productWholesaleService;

    @GetMapping
    private ResponseEntity<List<ProductWholeSaleResponse>> getAll() {
        var productsWholesale = productWholesaleService.getAll();
        return new ResponseEntity<>(productsWholesale, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ProductWholeSaleResponse> getById(@Valid @PathVariable(name = "id") Long id) {
        var productWholesale = productWholesaleService.getById(id);
        return new ResponseEntity<>(productWholesale, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<ProductWholeSaleResponse> create(@Valid @RequestBody ProductWholesaleRequest request) {
        var productWholesale = productWholesaleService.create(request);
        return new ResponseEntity<>(productWholesale, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<ProductWholeSaleResponse> update(@Valid @RequestBody ProductWholesaleRequest request,
            @Valid @PathVariable(name = "id") Long id) {
        var productWholesale = productWholesaleService.update(request, id);
        return new ResponseEntity<>(productWholesale, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@Valid @PathVariable(name = "id") Long id) {
        productWholesaleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
