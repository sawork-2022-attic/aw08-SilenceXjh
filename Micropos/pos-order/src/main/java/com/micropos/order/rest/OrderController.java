package com.micropos.order.rest;

import java.util.List;

import com.micropos.api.OrdersApi;
import com.micropos.dto.CartItemDto;
import com.micropos.dto.OrderDto;
import com.micropos.order.mapper.OrderMapper;
import com.micropos.order.model.Order;
import com.micropos.order.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class OrderController implements OrdersApi {
    
    private OrderMapper orderMapper;

    private OrderService orderService;

    @Autowired
    private StreamBridge streamBridge;
    
    @Autowired
    public OrderController(OrderMapper orderMapper, OrderService orderService) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<OrderDto> createOrder(List<CartItemDto> cartItemDto) {
        Order order = orderService.createOrder(orderMapper.toItems(cartItemDto));
        streamBridge.send("orderOutput", orderMapper.toOrderDto(order));
        return new ResponseEntity<>(orderMapper.toOrderDto(order), HttpStatus.OK);
    }

}