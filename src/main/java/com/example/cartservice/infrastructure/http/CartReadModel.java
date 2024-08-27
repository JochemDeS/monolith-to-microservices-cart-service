package com.example.cartservice.infrastructure.http;

import java.util.List;

public record CartReadModel(
        int id,
        List<CartItemReadModel> items,
        double totalPrice
) {
    private CartReadModel(Builder builder) {
        this(builder.id, builder.items, builder.totalPrice);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private List<CartItemReadModel> items;
        private double totalPrice;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder items(List<CartItemReadModel> items) {
            this.items = items;
            return this;
        }

        public Builder totalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public CartReadModel build() {
            return new CartReadModel(this);
        }
    }
}
