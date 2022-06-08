package com.micropos.carts.repository;

import java.util.ArrayList;
import java.util.List;

import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;

import org.springframework.stereotype.Repository;

@Repository
public class CartsRepository {
    
    private List<Cart> carts = new ArrayList<>();
    private int index = 0;

    public List<Cart> allCarts() {
        return carts;
    }

    public Cart createCart() {
        Cart cart = new Cart(index);
        index++;
        carts.add(cart);
        return cart;
    }

    public Cart findCart(int cartId) {
        for(Cart c : carts) {
            if(c.getCartId() == cartId) {
                return c;
            }
        }
        return null;
    }

}