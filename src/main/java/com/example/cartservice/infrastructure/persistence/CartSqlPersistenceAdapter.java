package com.example.cartservice.infrastructure.persistence;

import com.example.cartservice.application.cart.SaveCartPort;
import com.example.cartservice.application.cart.GetCartByUserIdPort;
import com.example.cartservice.application.cart.UpdateCartPort;
import com.example.cartservice.domain.Cart;
import com.example.cartservice.domain.CartId;
import com.example.cartservice.domain.UserId;
import org.springframework.stereotype.Component;

@Component
public class CartSqlPersistenceAdapter implements SaveCartPort, GetCartByUserIdPort, UpdateCartPort {
    private final CartRepository cartRepository;

    public CartSqlPersistenceAdapter(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public CartId save(UserId userId) {
        final var cart = CartEntity.builder()
                .userId(userId.value())
                .build();
        final var savedCart = cartRepository.save(cart);

        return CartId.builder()
                .value(savedCart.getId())
                .build();
    }

    @Override
    public Cart byUserId(UserId userId) {
        return CartMapper.toDomain(cartRepository.findByUserId(userId.value()));
    }

    @Override
    public void update(Cart cart, UserId userId) {
        final var cartEntity = CartMapper.toEntity(cart, userId);
        cartRepository.save(cartEntity);
    }
}
