package com.example.cartservice.application.common;

public interface UseCase<T, R> {
    R handle (T request);
}
