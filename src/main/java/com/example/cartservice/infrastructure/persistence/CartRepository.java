package com.example.cartservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findByUserId(long userId);
}
