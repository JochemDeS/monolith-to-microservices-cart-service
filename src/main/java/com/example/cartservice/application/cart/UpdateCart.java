package com.example.cartservice.application.cart;


import com.example.cartservice.domain.ProductId;
import com.example.cartservice.domain.UserId;

public record UpdateCart(UserId userId, ProductId productId, int quantity) {
    private UpdateCart(Builder builder) {
        this(builder.userId, builder.productId, builder.quantity);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UserId userId;
        private ProductId productId;
        private int quantity;

        public Builder userId(UserId user) {
            this.userId = user;
            return this;
        }

        public Builder productId(ProductId productId) {
            this.productId = productId;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public UpdateCart build() {
            return new UpdateCart(this);
        }
    }
}
