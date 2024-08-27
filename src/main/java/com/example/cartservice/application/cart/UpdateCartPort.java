package com.example.cartservice.application.cart;


import com.example.cartservice.domain.Cart;
import com.example.cartservice.domain.UserId;

public interface UpdateCartPort {
    void update(Cart cart, UserId userId);
}
