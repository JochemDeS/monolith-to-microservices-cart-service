package com.example.cartservice.domain;

public class CartItem {
    private final CartItemId id;
    private Product product;
    private int quantity;

    public CartItem(Builder builder) {
        this.id = builder.id;
        this.product = builder.product;
        this.quantity = builder.quantity;
    }

    public CartItemId getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private CartItemId id;
        private Product product;
        private int quantity;

        public Builder id(CartItemId id) {
            this.id = id;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public CartItem build() {
            return new CartItem(this);
        }
    }
}
