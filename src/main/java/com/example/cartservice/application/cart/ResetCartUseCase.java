package com.example.cartservice.application.cart;

import com.example.cartservice.application.common.UseCase;
import com.example.cartservice.domain.UserId;
import org.springframework.stereotype.Service;

@Service
public class ResetCartUseCase implements UseCase<UserId, Void> {
    private final GetCartByUserIdPort getCartByUserIdPort;
    private final UpdateCartPort updateCartPort;

    public ResetCartUseCase(GetCartByUserIdPort getCartByUserIdPort, UpdateCartPort updateCartPort) {
        this.getCartByUserIdPort = getCartByUserIdPort;
        this.updateCartPort = updateCartPort;
    }

    @Override
    public Void handle(UserId userId) {
        final var cart = getCartByUserIdPort.byUserId(userId);

        cart.reset();
        updateCartPort.update(cart, userId);
        return null;
    }
}
