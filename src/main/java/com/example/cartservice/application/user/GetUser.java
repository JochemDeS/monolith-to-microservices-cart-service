package com.example.cartservice.application.user;

import com.example.cartservice.domain.UserId;

import java.util.Optional;

public interface GetUser {
    Optional<UserId> byToken(String token);
}
