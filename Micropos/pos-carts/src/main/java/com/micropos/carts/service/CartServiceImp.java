package com.micropos.carts.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micropos.carts.mapper.CartMapper;
import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;
import com.micropos.carts.repository.CartsRepository;
import com.micropos.dto.CartItemDto;
import com.micropos.dto.OrderDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartServiceImp implements CartService {
    
    @Autowired
    private CartsRepository cartsRepository;

    @LoadBalanced
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CartMapper cartMapper;

    @Override
    @Cacheable(value = "cartCache", key = "#cartId")
    public Cart getCart(int cartId) {
        return cartsRepository.findCart(cartId);
    }

    @Override 
    public Cart createCart() {
        return cartsRepository.createCart();
    }

    @Override
    @Cacheable(value = "cartCache", key = "'carts'")
    public List<Cart> carts() {
        return cartsRepository.allCarts();
    }

    @Override
    public Cart addItemToCart(int cartId, Item item) {
        Cart cart = getCart(cartId);
        if(cart == null) 
            return null;
        cart.addItem(item);
        return cart;
    }

    @Override
    public double getTotal(int cartId) {
        Cart cart = getCart(cartId);
        if(cart == null) {
            return -1.0;
        }
        return cart.getTotal();
    }

    @Override
    public double checkout(int cartId) {
        Cart cart = getCart(cartId);
        if(cart == null) {
            return -1.0;
        }
        if(cart.getItems().isEmpty()) {
            return 0.0;
        }
        double total = cart.getTotal();
        List<CartItemDto> dtos = cartMapper.toItemDtos(cart.getItems());
        
        ObjectMapper mapper = new ObjectMapper();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(dtos), headers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        restTemplate.postForObject("http://ORDER-SERVICE:4444/api/orders", request, OrderDto.class);
        cart.clear();
        return total;
        
    }

}