package com.auction.auction.shop_auction.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.security.model.Customer;
import com.auction.auction.security.repository.CustomerRepository;
import com.auction.auction.shop_auction.dto.MessageAuctionRequest;
import com.auction.auction.shop_auction.dto.MessageAuctionResponse;
import com.auction.auction.shop_auction.model.MessageAuction;
import com.auction.auction.shop_auction.repository.AuctionRepository;
import com.auction.auction.shop_auction.repository.MessageAuctionRepository;
import com.auction.auction.shop_auction.service.MessageAuctionService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageAuctionServiceImpl implements MessageAuctionService {

    @Autowired
    private MessageAuctionRepository messageAuctionRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<MessageAuctionResponse> getAll() {
        var messages = messageAuctionRepository.findAll();
        var response = messages.stream().map(message -> mapper.map(message, MessageAuctionResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public List<MessageAuctionResponse> getMessagesByAuctionId(Long id) {
        var messages = messageAuctionRepository.getMessageAuctionsByAuctionId(id);
        var response = messages.stream().map(message -> mapper.map(message, MessageAuctionResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public MessageAuctionResponse getById(Long id) {
        var message = messageAuctionRepository.getMessageAuctionById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Message not found"));
        var response = mapper.map(message, MessageAuctionResponse.class);
        return response;
    }

    @Override
    public MessageAuctionResponse create(MessageAuctionRequest request) {

        var auction = auctionRepository.getAuctionById(request.getAuctionId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Auction not found"));

        if (auction.getPrice() >= request.getPrice()) {
            throw new ResourceNotFoundExceptionRequest("Invalid balance");
        }

        var customer = customerRepository.getCustomerById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Customer not found"));

        if (customer.getWallet() < request.getPrice()) {
            throw new ResourceNotFoundExceptionRequest("Insufficient balance");
        }

        var message = new MessageAuction();
        var price = auction.getPrice();

        message.setPrice(request.getPrice());
        message.setCustomer(customer);
        message.setAuction(auction);

        auction.setPrice(request.getPrice());

        var wallet = customer.getWallet() - request.getPrice();

        customer.setWallet(wallet);

        if (auction.getCustomer() != null) {
            var previosCustomer = auction.getCustomer();
            previosCustomer.setWallet(previosCustomer.getWallet() + price);
            var customers = new ArrayList<Customer>();
            customers.add(previosCustomer);
            customers.add(customer);
            try {
                customerRepository.saveAll(customers);
            } catch (Exception e) {
                throw new ResourceNotFoundExceptionRequest("Error ocurrred while updating customer");
            }
        } else {
            try {
                customerRepository.save(customer);
            } catch (Exception e) {
                throw new ResourceNotFoundExceptionRequest("Error ocurrred while updating customer");
            }
        }

        auction.setCustomer(customer);

        try {
            messageAuctionRepository.save(message);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurrred while updating message");
        }

        try {
            auctionRepository.save(auction);

        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurrred while updating auction price");
        }

        var response = mapper.map(message, MessageAuctionResponse.class);
        return response;
    }

    @Override
    public MessageAuctionResponse update(MessageAuctionRequest request, Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(Long id) {
        try {
            messageAuctionRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurrred while deleting auction price");
        }
    }

}
