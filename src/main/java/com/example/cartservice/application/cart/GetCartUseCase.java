package com.example.cartservice.application.cart;

import com.example.cartservice.application.common.UseCase;
import com.example.cartservice.application.product.GetProduct;
import com.example.cartservice.domain.Cart;
import com.example.cartservice.domain.Product;
import com.example.cartservice.domain.ProductId;
import com.example.cartservice.domain.UserId;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GetCartUseCase implements UseCase<UserId, Cart> {
    private final GetCartByUserIdPort getCartByUserIdPort;
    private final GetProduct getProduct;

    public GetCartUseCase(GetCartByUserIdPort getCartByUserIdPort, GetProduct getProduct) {
        this.getCartByUserIdPort = getCartByUserIdPort;
        this.getProduct = getProduct;
    }

    @Override
    public Cart handle(UserId request) {
        final var cart = getCartByUserIdPort.byUserId(request);
        final var productIds = cart.getItems().stream()
                .map(item -> item.getProduct().id())
                .toList();

        final var products = getProduct.byIdsIn(productIds);
        final Map<ProductId, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::id, product -> product));

        cart.getItems().forEach(item -> {
            final var updatedProduct = productMap.get(item.getProduct().id());
            if (updatedProduct != null) {
                item.setProduct(updatedProduct);
            }
        });

        return cart;
    }
}
