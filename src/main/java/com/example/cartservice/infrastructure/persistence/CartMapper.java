package com.example.cartservice.infrastructure.persistence;

import com.example.cartservice.domain.*;

import java.util.stream.Collectors;

public class CartMapper {
    public static Cart toDomain(CartEntity cartEntity) {
        final var items = cartEntity.getItems().stream()
                .map(item -> CartItem.builder()
                        .id(CartItemId.builder()
                                .value(item.getId())
                                .build())
                        .product(Product.builder()
                                .id(ProductId.builder()
                                    .value(item.getProductId())
                                    .build())
                                .build())
                        .quantity(item.getQuantity())
                        .build()
                )
                .collect(Collectors.toList());

        return Cart.builder()
                .id(CartId.builder()
                        .value(cartEntity.getId())
                        .build())
                .items(items)
                .build();
    }

    public static CartEntity toEntity(Cart cart, UserId userId) {
        final var cartEntity = CartEntity.builder()
                .id(cart.getId().value())
                .userId(userId.value())
                .build();

        final var items = cart.getItems().stream()
                .map(item -> CartItemEntity.builder()
                        .cart(cartEntity)
                        .productId(item.getProduct()
                                .id()
                                .value())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());

        cartEntity.setItems(items);

        return cartEntity;
    }
}
