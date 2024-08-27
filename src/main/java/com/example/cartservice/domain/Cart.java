package com.example.cartservice.domain;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final CartId id;
    private final List<CartItem> items;

    private Cart(Builder builder) {
        this.id = builder.id;
        this.items = builder.items;
    }

    public void add(CartItem item) {
        final var existingItem = items.stream()
                .filter(cartItem -> cartItem.getProduct().id().equals(item.getProduct().id()))
                .findFirst();

        existingItem.ifPresentOrElse(
                cartItem -> cartItem.increaseQuantity(item.getQuantity()),
                () -> items.add(item));
    }

    public void remove(CartItem item) {
        items.stream()
                .filter(cartItem -> cartItem.getProduct().id().equals(item.getProduct().id()))
                .findFirst()
                .ifPresent(items::remove);
    }

    public void reset() {
        items.clear();
    }

    public CartId getId() {
        return id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return items.stream().mapToDouble(item -> item.getProduct().price() * item.getQuantity()).sum();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private CartId id;
        private List<CartItem> items = new ArrayList<>();

        public Builder id(CartId id) {
            this.id = id;
            return this;
        }

        public Builder items(List<CartItem> items) {
            this.items = items;
            return this;
        }

        public Cart build() {
            return new Cart(this);
        }
    }
}
