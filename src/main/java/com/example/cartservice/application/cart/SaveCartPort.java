package com.example.cartservice.application.cart;

import com.example.cartservice.domain.CartId;
import com.example.cartservice.domain.UserId;

public interface SaveCartPort {
    CartId save(UserId userId);
}
