package com.micropos.carts.service;

import java.util.List;

import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;

public interface CartService {

    public Cart getCart(int cartId);

    public Cart createCart();

    public List<Cart> carts();
    
    public Cart addItemToCart(int cartId, Item item);

    public double getTotal(int cartId);

    public double checkout(int cartId);

}