package com.auction.auction.shop_unit.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.auction.auction.exception.ResourceNotFoundExceptionRequest;
import com.auction.auction.shop_unit.dto.OrderProductUnitRequest;
import com.auction.auction.shop_unit.dto.OrderProductUnitResponse;
import com.auction.auction.shop_unit.model.OrderProductUnit;
import com.auction.auction.shop_unit.model.OrderUnit;
import com.auction.auction.shop_unit.model.ProductUnit;
import com.auction.auction.shop_unit.repository.OrderProductUnitRepository;
import com.auction.auction.shop_unit.repository.OrderUnitRepository;
import com.auction.auction.shop_unit.repository.ProductUnitRepository;
import com.auction.auction.shop_unit.service.OrderProductUnitService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProductUnitServiceImpl implements OrderProductUnitService {

    @Autowired
    private OrderProductUnitRepository orderProductUnitRepository;

    @Autowired
    private OrderUnitRepository orderUnitRepository;

    @Autowired
    private ProductUnitRepository productUnitRepository;

    @Autowired
    private ModelMapper mapper;

    public class DoubleWrapper {
        public Double value;
    }

    @Override
    public List<OrderProductUnitResponse> getAll() {
        var orderProducts = orderProductUnitRepository.findAll();
        var response = orderProducts.stream()
                .map(orderProduct -> mapper.map(orderProduct, OrderProductUnitResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public List<OrderProductUnitResponse> create(OrderProductUnitRequest request) {
        var orderUnit = new OrderUnit();
        orderUnit.setCreated_order(request.getDate_created());
        try {
            orderUnitRepository.save(orderUnit);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while creating order unit");
        }

        var orderProductUnits = new ArrayList<OrderProductUnit>();
        var productUnits = new ArrayList<ProductUnit>();

        final DoubleWrapper a = new DoubleWrapper();
        a.value = 0.0;

        request.getProductUnitQuantifies().forEach(productDetail -> {
            var order = new OrderProductUnit();

            var productUnit = productUnitRepository.getProductUnitById(productDetail.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Product Unit not found"));
            var quantify = productUnit.getStock() - productDetail.getQuantify();
            var updateMinStock = productUnit.getMinStock() + productDetail.getQuantify();
            a.value = a.value + (productDetail.getQuantify() * productUnit.getPrice());
            if (quantify < 0) {
                throw new ResourceNotFoundExceptionRequest("Stock not avaible");
            }
            productUnit.setStock(quantify);
            productUnit.setMinStock(updateMinStock);
            order.setProductUnit(productUnit);
            order.setOrderUnit(orderUnit);
            order.setQuantify(productDetail.getQuantify());

            orderProductUnits.add(order);
            productUnits.add(productUnit);
        });

        orderProductUnitRepository.saveAll(orderProductUnits);
        productUnitRepository.saveAll(productUnits);

        orderUnit.setTotal(a.value);
        orderUnitRepository.save(orderUnit);

        var response = orderProductUnits.stream()
                .map(orderProduct -> mapper.map(orderProduct, OrderProductUnitResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public List<OrderProductUnitResponse> getByOrderId(Long id) {
        var orderProducts = orderProductUnitRepository.getAllByOrderId(id);
        var response = orderProducts.stream()
                .map(orderProduct -> mapper.map(orderProduct, OrderProductUnitResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public void deleteByOrderId(Long id) {
        var order = orderUnitRepository.getOrderUnitById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionRequest("Order unit not found"));
        var orderProducts = orderProductUnitRepository.getAllByOrderId(id);
        try {
            orderProducts.forEach(orderProduct -> {
                orderProductUnitRepository.delete(orderProduct);
            });
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting all order product by order id");
        }
        try {
            orderUnitRepository.delete(order);
        } catch (Exception e) {
            throw new ResourceNotFoundExceptionRequest("Error ocurred while deleting order by id");
        }
    }

}
