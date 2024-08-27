package com.example.cartservice.application.cart;

import com.example.cartservice.application.common.UseCase;
import com.example.cartservice.application.product.GetProduct;
import com.example.cartservice.domain.CartItem;
import com.example.cartservice.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class UpdateCartUseCase implements UseCase<UpdateCart, Void> {
    private final GetProduct getProduct;
    private final GetCartByUserIdPort getCartByUserIdPort;
    private final UpdateCartPort updateCartPort;

    public UpdateCartUseCase(GetProduct getProduct,
                             GetCartByUserIdPort getCartByUserIdPort,
                             UpdateCartPort updateCartPort) {
        this.getProduct = getProduct;
        this.getCartByUserIdPort = getCartByUserIdPort;
        this.updateCartPort = updateCartPort;
    }

    @Override
    public Void handle(UpdateCart request) {
        final var product = getProduct.byId(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        final var cart = getCartByUserIdPort.byUserId(request.userId());

        final var item = CartItem.builder()
                        .product(Product.builder()
                                .id(product.id())
                                .build())
                        .quantity(request.quantity())
                        .build();

        if (request.quantity() == 0) {
            cart.remove(item);
        } else {
            cart.add(item);
        }

        updateCartPort.update(cart, request.userId());
        return null;
    }
}
