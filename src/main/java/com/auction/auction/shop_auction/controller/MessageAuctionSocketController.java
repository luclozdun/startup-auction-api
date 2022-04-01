package com.auction.auction.shop_auction.controller;

import com.auction.auction.shop_auction.dto.MessageAuctionRequest;
import com.auction.auction.shop_auction.dto.MessageAuctionResponse;
import com.auction.auction.shop_auction.service.MessageAuctionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageAuctionSocketController {

    @Autowired
    private MessageAuctionService messageAuctionService;

    @MessageMapping("/message")
    @SendTo("/chat/message")
    public MessageAuctionResponse getAllWhenSendMessage(
            MessageAuctionRequest request) {
        var message = messageAuctionService.create(request);
        return message;
    }

}
