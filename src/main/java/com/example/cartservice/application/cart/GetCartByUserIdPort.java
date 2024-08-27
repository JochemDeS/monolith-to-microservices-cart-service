package com.example.cartservice.application.cart;


import com.example.cartservice.domain.Cart;
import com.example.cartservice.domain.UserId;


public interface GetCartByUserIdPort {
    Cart byUserId(UserId userId);
}
