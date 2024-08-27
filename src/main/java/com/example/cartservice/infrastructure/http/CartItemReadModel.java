package com.example.cartservice.infrastructure.http;

public record CartItemReadModel(
        int productId,
        String name,
        String thumbnail,
        double price,
        String brand,
        String category,
        int quantity
) {
    private CartItemReadModel(Builder builder) {
        this(builder.productId, builder.name, builder.thumbnail, builder.price, builder.brand, builder.category, builder.quantity);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int productId;
        private String name;
        private String thumbnail;
        private double price;
        private String brand;
        private String category;
        private int quantity;

        public Builder productId(int productId) {
            this.productId = productId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder thumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public CartItemReadModel build() {
            return new CartItemReadModel(this);
        }
    }
}
