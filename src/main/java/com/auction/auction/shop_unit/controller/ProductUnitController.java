package com.auction.auction.shop_unit.controller;

import java.util.List;

import javax.validation.Valid;

import com.auction.auction.shop_unit.dto.ProductUnitRequest;
import com.auction.auction.shop_unit.dto.ProductUnitResponse;
import com.auction.auction.shop_unit.service.ProductUnitService;

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
@RequestMapping("/products-unit")
public class ProductUnitController {
    @Autowired
    private ProductUnitService productUnitService;

    @GetMapping
    private ResponseEntity<List<ProductUnitResponse>> getAll() {
        var productsUnit = productUnitService.getAll();
        return new ResponseEntity<>(productsUnit, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@Valid @PathVariable(name = "id") Long id) {
        productUnitService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ProductUnitResponse> getById(@Valid @PathVariable(name = "id") Long id) {
        var response = productUnitService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<ProductUnitResponse> create(@Valid @RequestBody ProductUnitRequest request) {
        var response = productUnitService.create(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<ProductUnitResponse> update(@Valid @PathVariable(name = "id") Long id,
            @Valid @RequestBody ProductUnitRequest request) {
        var response = productUnitService.update(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
