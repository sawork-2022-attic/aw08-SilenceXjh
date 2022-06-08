package com.micropos.carts.rest;

import java.util.ArrayList;
import java.util.List;

import com.micropos.api.CartsApi;
import com.micropos.carts.mapper.CartMapper;
import com.micropos.carts.model.Cart;
import com.micropos.carts.service.CartService;
import com.micropos.dto.CartDto;
import com.micropos.dto.CartItemDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class CartController implements CartsApi {

    private CartMapper cartMapper;
    private CartService cartService;

    CartController(CartMapper cartMapper, CartService cartService) {
        this.cartMapper = cartMapper;
        this.cartService = cartService;
    }
    
    @Override
    public ResponseEntity<CartDto> addItemToCart(Integer cartId, CartItemDto cartItemDto) {
        Cart cart = cartService.addItemToCart(cartId, cartMapper.toItem(cartItemDto));
        if(cart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartMapper.toCartDto(cart), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CartDto> createCart() {
        Cart cart = cartService.createCart();
        if(cart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartMapper.toCartDto(cart), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CartDto> getCartById(Integer cartId) {
        Cart cart = cartService.getCart(cartId);
        if(cart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartMapper.toCartDto(cart), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CartDto>> listCarts() {
        List<Cart> carts = cartService.carts();
        if(carts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ArrayList<>(cartMapper.toCartDtos(carts)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Double> showCartTotal(Integer cartId) {
        double total = cartService.getTotal(cartId);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Double> checkout(Integer cartId) {
        double total = cartService.checkout(cartId);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

}