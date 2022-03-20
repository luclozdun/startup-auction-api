package com.auction.auction.shop_auction.controller;

import java.util.List;

import javax.validation.Valid;

import com.auction.auction.shop_auction.dto.AuctionResponse;
import com.auction.auction.shop_auction.dto.MessageAuctionRequest;
import com.auction.auction.shop_auction.dto.MessageAuctionResponse;
import com.auction.auction.shop_auction.service.MessageAuctionService;

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
@RequestMapping("/messages-auction")
public class MessageAuctionController {

    @Autowired
    private MessageAuctionService messageAuctionService;

    @GetMapping
    private ResponseEntity<List<MessageAuctionResponse>> getAll() {
        var messages = messageAuctionService.getAll();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/auction/{id}")
    private ResponseEntity<List<MessageAuctionResponse>> getAllByAuctionId(@Valid @PathVariable(name = "id") Long id) {
        var messages = messageAuctionService.getMessagesByAuctionId(id);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<MessageAuctionResponse> getById(@Valid @PathVariable(name = "id") Long id) {
        var message = messageAuctionService.getById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<AuctionResponse> create(@Valid @RequestBody MessageAuctionRequest request) {
        var message = messageAuctionService.create(request);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<MessageAuctionResponse> update(@Valid @PathVariable(name = "id") Long id,
            @Valid @RequestBody MessageAuctionRequest request) {
        var message = messageAuctionService.update(request, id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@Valid @PathVariable(name = "id") Long id) {
        messageAuctionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
